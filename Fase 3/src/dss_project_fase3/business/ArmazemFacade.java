package dss_project_fase3.business;

import dss_project_fase3.business.Enums.ZonaArmazem;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Robot;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Palete.*;

import java.util.*;
import java.util.stream.Collectors;

public class ArmazemFacade implements IArmazemFacade{
    private Set<Prateleira> prateleirasUsadas;
    private Set<Prateleira> prateleirasLivres;
    private Map<QR_Code, Palete> paletes;

    public ArmazemFacade(Set<Prateleira> prateleirasUsadas, Set<Prateleira> prateleirasLivres, Map<QR_Code, Palete> paletes) {
        this.prateleirasUsadas = prateleirasUsadas;
        this.prateleirasLivres = prateleirasLivres;
        this.paletes = paletes;
    }

    public ArmazemFacade(int nrCorredores, int nrSetores) {
        this.prateleirasUsadas = new TreeSet<>();
        this.prateleirasLivres = new TreeSet<>();
        this.paletes = new HashMap<>();

        for (int i = 0; i < nrCorredores; i++) {
            for (int j = 0; i < nrSetores; i++) {
                Prateleira p = new Prateleira(new Localizacao_Armazenamento(i,j));
                this.prateleirasLivres.add(p);
            }
        }

        paletes.put(new QR_Code("dasdasda"), new Palete(new QR_Code("dasdasda"), new Material("gelado"), new Localizacao_Armazenamento(1,1)));
        paletes.put(new QR_Code("111"), new Palete(new QR_Code("111"), new Material("camelos"), new Localizacao_Armazenamento(1,2)));
        paletes.put(new QR_Code("222"), new Palete(new QR_Code("222"), new Material("pinos"), new Localizacao_Robot(1)));
        paletes.put(new QR_Code("333"), new Palete(new QR_Code("333"), new Material("cones"), new Localizacao_Transporte(ZonaArmazem.ZONA_ENTREGA)));
        paletes.put(new QR_Code("444"), new Palete(new QR_Code("444"), new Material("hjashas"), new Localizacao_Transporte(ZonaArmazem.ZONA_RECECAO)));

    }


    @Override
    public void comunicar_codigo_qr(QR_Code qr_code) {

    }

    @Override
    public void comunicar_ordem_transporte() {

    }

    @Override
    public void notificar_recolha_palete(Palete palete) {

    }

    @Override
    public void notificar_entrega_palete(Palete palete) {

    }

    @Override
    public List<Palete> consultar_listagem_localizacoes() {
        return this.paletes
                .values()
                .stream()
                .map(Palete::clone)
                .collect(Collectors.toList());
    }
}
