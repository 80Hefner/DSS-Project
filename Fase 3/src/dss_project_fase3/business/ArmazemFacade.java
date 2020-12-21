package dss_project_fase3.business;

import dss_project_fase3.business.Enums.ZonaArmazem;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Robot;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Palete.*;

import java.util.*;
import java.util.stream.Collectors;

public class ArmazemFacade implements IArmazemFacade{
    private List<Prateleira> prateleirasUsadas;
    private List<Prateleira> prateleirasLivres;
    private Map<QR_Code, Palete> paletes;
    private List<Palete> paletes_para_transporte;
    private Robot robot;

    public ArmazemFacade(int nrCorredores, int nrSetores) {
        this.prateleirasUsadas = new ArrayList<>();
        this.prateleirasLivres = new ArrayList<>();
        this.paletes = new HashMap<>();
        this.paletes_para_transporte = new ArrayList<>();

        for (int i = 0; i < nrCorredores; i++) {
            for (int j = 0; i < nrSetores; i++) {
                Prateleira p = new Prateleira(new Localizacao_Armazenamento(i,j));
                this.prateleirasLivres.add(p);
            }
        }

//        paletes.put(new QR_Code("QR_CODE_BEGIN&&gelado&&QR_CODE_END"), new Palete(new QR_Code("dasdasda"), new Material("gelado"), new Localizacao_Armazenamento(1,1)));
//        paletes.put(new QR_Code("111"), new Palete(new QR_Code("111"), new Material("camelos"), new Localizacao_Armazenamento(1,2)));
//        paletes.put(new QR_Code("222"), new Palete(new QR_Code("222"), new Material("pinos"), new Localizacao_Robot(1)));
        Palete p4 = new Palete(new QR_Code("QR_CODE_BEGIN&&penas&&QR_CODE_END"), new Material("penas"), new Localizacao_Transporte(ZonaArmazem.ZONA_RECECAO));
        paletes.put(p4.getQr_code(), p4);
        paletes_para_transporte.add(p4);
        this.robot = new Robot(1);
    }


    @Override
    public void comunicar_codigo_qr(QR_Code qr_code) {
        Palete p = new Palete (qr_code.clone(), new Material(qr_code.getMaterial()), new Localizacao_Transporte(ZonaArmazem.ZONA_RECECAO));

        this.paletes.put(qr_code.clone(), p);
        this.paletes_para_transporte.add(p);
    }

    @Override
    public void comunicar_ordem_transporte() {
        if (this.paletes_para_transporte.isEmpty()) return;

        Palete pal = this.paletes_para_transporte.remove(0);

        Prateleira prat = getPrateleiraDisponivel();
        this.prateleirasLivres.remove(prat);
        this.prateleirasUsadas.add(prat);

        Entrega e = new Entrega(pal, pal.getLocalizacao(), prat.getLocalizacao());

        this.robot.pegarEntrega(e);
    }

    @Override
    public void notificar_recolha_palete(QR_Code qr_code) {
        this.paletes
                .keySet()
                .stream()
                .filter(k -> k.equals(qr_code))
                .findAny()
                .ifPresent(qrCode -> this.paletes.get(qrCode).setLocalizacao(new Localizacao_Robot(1)));

        //System.out.println(this.robot.toString("\t"));
    }

    @Override
    public void notificar_entrega_palete(QR_Code qr_code) {
        this.paletes
                .keySet()
                .stream()
                .filter(k -> k.equals(qr_code))
                .findAny()
                .ifPresent(qrCode -> {
                    this.paletes.get(qrCode).setLocalizacao(this.robot.getEntregaAtual().getDestino());
                    //System.out.println(this.paletes.get(qrCode).toString("\t"));
                    this.robot.pousarEntrega();
                });

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
        return  this.prateleirasLivres.get(0);
    }
}
