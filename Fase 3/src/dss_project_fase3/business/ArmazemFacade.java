package dss_project_fase3.business;

import dss_project_fase3.business.Comparators.ComparatorDistanciaRobots;
import dss_project_fase3.business.Enums.RobotRequest;
import dss_project_fase3.business.Enums.ZonaArmazem;
import dss_project_fase3.business.Exceptions.EmptyTransportQueueException;
import dss_project_fase3.business.Exceptions.InvalidRequestFromRobot;
import dss_project_fase3.business.Exceptions.InvalidRobotIDException;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Robot;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Palete.*;
import dss_project_fase3.business.Prateleira.Prateleira;
import dss_project_fase3.business.Robot.Entrega;
import dss_project_fase3.business.Robot.Robot;
import dss_project_fase3.data.*;

import java.util.*;

public class ArmazemFacade implements IArmazemFacade{
    private IPrateleiraDAO prateleiras;
    private Set<Prateleira> prateleirasLivres;
    private IPaleteDAO paletes;
    private List<String> paletes_para_transporte;
    private IRobotDAO robots;

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
     */
    @Override
    public void comunicar_ordem_transporte() throws EmptyTransportQueueException {
        if (this.paletes_para_transporte.isEmpty()) throw new EmptyTransportQueueException();

        String qr_code = this.paletes_para_transporte.remove(0);
        Palete pal = this.paletes.get(qr_code);

        Prateleira prat = getPrateleiraDisponivel();
        this.prateleirasLivres.remove(prat);

        Entrega e = new Entrega(pal.getQr_code().getCodigo(), pal.getLocalizacao(), prat.getLocalizacao());

        Robot r = getMelhorRobotDisponivel();
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
     * Função que procura a palete disponível mais próxima.
     * @return      Prateleira Selecionada
     */
    private Prateleira getPrateleiraDisponivel() {  // TODO: esta uma merda
        final Prateleira[] prateleira = {null};
        this.prateleirasLivres
                .stream()
                .findAny()
                .ifPresent(p -> prateleira[0] = p);

        return prateleira[0];
    }

    /**
     * Função que procura o robot disponível mais próximo
     * @return      Robot selecionado
     */
    private Robot getMelhorRobotDisponivel() {  // TODO: esta uma merda
        final Robot[] robot = {null};
        this.robots
                .values()
                .stream()
                .filter(Robot::isDisponivel)
                .sorted(new ComparatorDistanciaRobots())
                .findFirst()
                .ifPresent(r -> robot[0] = r);

        return robot[0];
    }
}
