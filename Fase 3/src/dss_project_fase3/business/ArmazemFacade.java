package dss_project_fase3.business;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.utils.Enums.RobotRequest;
import dss_project_fase3.utils.Enums.TransportOrderError;
import dss_project_fase3.utils.Enums.ZonaArmazem;
import dss_project_fase3.utils.Exceptions.EmptyTransportQueueException;
import dss_project_fase3.utils.Exceptions.InvalidRequestFromRobot;
import dss_project_fase3.utils.Exceptions.InvalidRobotIDException;
import dss_project_fase3.utils.Exceptions.InvalidTransportOrderException;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Robot;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Palete.*;
import dss_project_fase3.business.Prateleira.Prateleira;
import dss_project_fase3.business.Robot.Entrega;
import dss_project_fase3.business.Robot.Robot;
import dss_project_fase3.data.*;

import java.util.*;
import java.util.stream.Collectors;

public class ArmazemFacade implements IArmazemFacade{
    private IPrateleiraDAO prateleiras;
    private Set<Prateleira> prateleirasLivres;
    private IPaleteDAO paletes;
    private List<String> paletes_para_transporte;
    private IRobotDAO robots;
    private GrafoArmazem grafo_armazem;

    /**
     * Construtor parametrizado do ArmazemFacade
     * @param nrCorredores      numero de Corredores do Armazem
     * @param nrSetores         numero de Setores do Armazem
     */
    public ArmazemFacade(int nrCorredores, int nrSetores) {
        this.prateleiras = PrateleiraDAO.getInstance();
        this.prateleirasLivres = new TreeSet<>();
        this.paletes = PaleteDAO.getInstance();
        this.paletes_para_transporte = new ArrayList<>();
        this.robots = RobotDAO.getInstance();
        this.grafo_armazem = new GrafoArmazem();

        this.paletes
                .values()
                .stream()
                .filter(p -> p.getLocalizacao().getZona().equals(ZonaArmazem.ZONA_RECECAO))
                .forEach(p -> this.paletes_para_transporte.add(p.getQr_code().getCodigo()));

        this.prateleiras
                .stream()
                .filter(p -> p.getQr_code() == null)
                .forEach(p -> this.prateleirasLivres.add(p));

    }


    /**
     * Função responsável por comunicar um Código QR ao Sistema
     * @param qr_code       QR_Code a ser comunicado
     */
    @Override
    public void comunicar_codigo_qr(QR_Code qr_code) {
        Palete p = new Palete (qr_code.clone(), new Material(qr_code.getMaterial()), new Localizacao_Transporte(ZonaArmazem.ZONA_RECECAO));

        this.paletes.put(qr_code.getCodigo(), p);
        this.paletes_para_transporte.add(p.getQr_code().getCodigo());
    }

    /**
     * Função responsável por comunicar uma ordem de transporte
     * @throws EmptyTransportQueueException     Excepção de quando não existem paletes para serem transportadas
     * @throws InvalidTransportOrderException   Exceção de quando não existe armazenamento disponível ou robots disponíveis
     */
    @Override
    public void comunicar_ordem_transporte() throws EmptyTransportQueueException, InvalidTransportOrderException {
        if (this.paletes_para_transporte.isEmpty()) throw new EmptyTransportQueueException();

        String qr_code = this.paletes_para_transporte.get(0);
        Palete pal = this.paletes.get(qr_code);

        Prateleira prat = getMelhorPrateleiraDisponivel();

        Entrega e = new Entrega(pal.getQr_code().getCodigo(), pal.getLocalizacao(), prat.getLocalizacao());

        Robot r = this.robots.get(getMelhorRobotDisponivel(pal.getLocalizacao()));


        this.paletes_para_transporte.remove(0);
        this.prateleirasLivres.remove(prat);
        this.robots.recolhePalete(r.getId_robot(), e);
    }

    /**
     * Função responsável por comunicar recolha da Palete por parte do Robot
     * @param id_robot                      id do Robot que recolhe a palete
     * @throws InvalidRobotIDException      Excepção de quando id do robot é inválido
     * @throws InvalidRequestFromRobot      Exceção de quando recolha não pode ser efetuada
     */
    @Override
    public void notificar_recolha_palete(int id_robot) throws InvalidRobotIDException, InvalidRequestFromRobot {
        Robot robot = this.robots.get(id_robot);

        if (robot == null) throw new InvalidRobotIDException();

        Entrega entrega = robot.getEntregaAtual();

        if (entrega == null || this.paletes.get(entrega.getQr_code()).getLocalizacao().getZona().equals(ZonaArmazem.ROBOT)) throw new InvalidRequestFromRobot(RobotRequest.RECOLHA);

        String qr_code = entrega.getQr_code();


        this.paletes.atualizaLocalizacao(new Localizacao_Robot(id_robot), qr_code);
        this.robots.encontraChegada(id_robot, entrega.getOrigem());
    }

    /**
     * Função responsável por comunicar entrega da Palete por parte do Robot
     * @param id_robot                      id do Robot que recolhe a palete
     * @throws InvalidRobotIDException      Excepção de quando id do robot é inválido
     * @throws InvalidRequestFromRobot      Exceção de quando entrega não pode ser efetuada
     */
    @Override
    public void notificar_entrega_palete(int id_robot) throws InvalidRobotIDException, InvalidRequestFromRobot {
        Robot robot = this.robots.get(id_robot);

        if (robot == null) throw new InvalidRobotIDException();

        Entrega entrega = robot.getEntregaAtual();

        if (entrega == null || !this.paletes.get(entrega.getQr_code()).getLocalizacao().getZona().equals(ZonaArmazem.ROBOT)) throw new InvalidRequestFromRobot(RobotRequest.ENTREGA);

        String qr_code = entrega.getQr_code();


        this.paletes.atualizaLocalizacao(this.robots.get(id_robot).getEntregaAtual().getDestino(), qr_code);
        this.prateleiras.inserePalete((Localizacao_Armazenamento) this.robots.get(id_robot).getEntregaAtual().getDestino(), qr_code);

        this.robots.entregaRealizada(id_robot);
    }

    /**
     * Função que recolhe a listagem de todas as paletes no Sistema
     * @return      List com todas as Paletes do Sistema
     */
    @Override
    public List<Palete> consultar_listagem_localizacoes() {
        return new ArrayList<>(this.paletes.values());
    }

    /**
     * Função que procura a prateleira disponível mais próxima.
     * @return      Prateleira Selecionada
     */
    private Prateleira getMelhorPrateleiraDisponivel() throws InvalidTransportOrderException {  // TODO: esta uma merda
        if (this.prateleirasLivres.isEmpty()) throw new InvalidTransportOrderException(TransportOrderError.ARMAZEM_CHEIO);

        return this.grafo_armazem.getMelhorPrateleira(this.prateleirasLivres);
    }

    /**
     * Função que procura o robot disponível mais próximo
     * @return      ID do Robot selecionado
     */
    private int getMelhorRobotDisponivel(Localizacao origem) throws InvalidTransportOrderException {
        List<Robot> robots_disponiveis = this.robots.values().stream().filter(Robot::isDisponivel).collect(Collectors.toList());

        if (robots_disponiveis.isEmpty()) throw new InvalidTransportOrderException(TransportOrderError.ROBOTS_INDISPONIVEIS);

        return this.grafo_armazem.getMelhorRobot(robots_disponiveis, origem);
    }
}
