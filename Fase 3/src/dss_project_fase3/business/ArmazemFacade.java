package dss_project_fase3.business;

import dss_project_fase3.business.Enums.ZonaArmazem;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Robot;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Palete.*;
import dss_project_fase3.data.IPaleteDAO;
import dss_project_fase3.data.PaleteDAO;

import java.util.*;
import java.util.stream.Collectors;

public class ArmazemFacade implements IArmazemFacade{
    private List<Prateleira> prateleirasUsadas;
    private List<Prateleira> prateleirasLivres;
    private IPaleteDAO paletes;
    private List<Palete> paletes_para_transporte;
    private Map<Integer, Robot> robots;

    public ArmazemFacade(int nrCorredores, int nrSetores) {
        this.prateleirasUsadas = new ArrayList<>();
        this.prateleirasLivres = new ArrayList<>();
        this.paletes = PaleteDAO.getInstance();
        this.paletes_para_transporte = new ArrayList<>();
        this.robots = new HashMap<>();

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
                .forEach(p -> this.paletes_para_transporte.add(p));

        this.robots.put(1, new Robot(1));
        this.robots.put(2, new Robot(2));
    }


    @Override
    public void comunicar_codigo_qr(QR_Code qr_code) {
        Palete p = new Palete (qr_code.clone(), new Material(qr_code.getMaterial()), new Localizacao_Transporte(ZonaArmazem.ZONA_RECECAO));

        this.paletes.put(qr_code.getCodigo(), p);
        this.paletes_para_transporte.add(p);
    }

    @Override
    public void comunicar_ordem_transporte() {
        if (this.paletes_para_transporte.isEmpty()) return;

        Palete pal = this.paletes_para_transporte.remove(0);

        Prateleira prat = getPrateleiraDisponivel();
        this.prateleirasLivres.remove(prat);
        this.prateleirasUsadas.add(prat);

        Entrega e = new Entrega(pal.getQr_code().getCodigo(), pal.getLocalizacao(), prat.getLocalizacao());

        Robot r = getMelhorRobotDisponivel();
        r.pegarEntrega(e);
    }

    @Override
    public void notificar_recolha_palete(QR_Code qr_code, int id_robot) {
        this.paletes.atualizaLocalizacao(new Localizacao_Robot(id_robot), qr_code.getCodigo());

        //System.out.println(this.robot.toString("\t"));
    }

    @Override
    public void notificar_entrega_palete(QR_Code qr_code, int id_robot) {
        this.paletes.atualizaLocalizacao(this.robots.get(id_robot).getEntregaAtual().getDestino(), qr_code.getCodigo());

        this.robots.get(id_robot).pousarEntrega();

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

    private Prateleira getPrateleiraDisponivel() {
        return this.prateleirasLivres.get(0);
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
