package dss_project_fase3.business;

import dss_project_fase3.business.Enums.ZonaArmazem;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Robot;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Palete.*;
import dss_project_fase3.data.*;

import java.util.*;
import java.util.stream.Collectors;

public class ArmazemFacade implements IArmazemFacade{
    private IPrateleiraDAO prateleiras;
    private Set<Prateleira> prateleirasLivres;
    private IPaleteDAO paletes;
    private List<String> paletes_para_transporte;
    private IRobotDAO robots;

    public ArmazemFacade(int nrCorredores, int nrSetores) {
        this.prateleiras = PrateleiraDAO.getInstance();
        this.prateleirasLivres = new TreeSet<>();
        this.paletes = PaleteDAO.getInstance();
        this.paletes_para_transporte = new ArrayList<>();
        this.robots = RobotDAO.getInstance();

        for (int i = 0; i < nrCorredores; i++) {
            for (int j = 0; i < nrSetores; i++) {
                Prateleira p = new Prateleira(new Localizacao_Armazenamento(i,j));
                this.prateleirasLivres.add(p);
            }
        }

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


    @Override
    public void comunicar_codigo_qr(QR_Code qr_code) {
        Palete p = new Palete (qr_code.clone(), new Material(qr_code.getMaterial()), new Localizacao_Transporte(ZonaArmazem.ZONA_RECECAO));

        this.paletes.put(qr_code.getCodigo(), p);
        this.paletes_para_transporte.add(p.getQr_code().getCodigo());
    }

    @Override
    public void comunicar_ordem_transporte() {
        if (this.paletes_para_transporte.isEmpty()) return;

        String qr_code = this.paletes_para_transporte.remove(0);
        Palete pal = this.paletes.get(qr_code);

        Prateleira prat = getPrateleiraDisponivel();
        this.prateleirasLivres.remove(prat);

        Entrega e = new Entrega(pal.getQr_code().getCodigo(), pal.getLocalizacao(), prat.getLocalizacao());

        Robot r = getMelhorRobotDisponivel();
        this.robots.recolhePalete(r.getId_robot(), e);
    }

    @Override
    public void notificar_recolha_palete(QR_Code qr_code, int id_robot) {
        this.paletes.atualizaLocalizacao(new Localizacao_Robot(id_robot), qr_code.getCodigo());

        //System.out.println(this.robot.toString("\t"));
    }

    @Override
    public void notificar_entrega_palete(QR_Code qr_code, int id_robot) {
        this.paletes.atualizaLocalizacao(this.robots.get(id_robot).getEntregaAtual().getDestino(), qr_code.getCodigo());
        this.prateleiras.inserePalete((Localizacao_Armazenamento) this.robots.get(id_robot).getEntregaAtual().getDestino(), qr_code.getCodigo());

        this.robots.entregaRealizada(id_robot);

        //System.out.println(this.robot.toString("\t"));
    }

    @Override
    public List<Palete> consultar_listagem_localizacoes() {
        return this.paletes
                .values()
                .stream()
                .map(Palete::clone)
                .collect(Collectors.toList());
    }

    private Prateleira getPrateleiraDisponivel() {  // TODO: esta uma merda
        final Prateleira[] prateleira = {null};
        this.prateleirasLivres
                .stream()
                .findAny()
                .ifPresent(p -> prateleira[0] = p);

        return prateleira[0];
    }

    private Robot getMelhorRobotDisponivel() {  // TODO: esta uma merda
        final Robot[] robot = {null};
        this.robots
                .values()
                .stream()
                .filter(Robot::isDisponivel)
                .findFirst()
                .ifPresent(r -> robot[0] = r);

        return robot[0];
    }
}
