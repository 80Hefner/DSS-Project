package dss_project_fase3.business;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;

public class Prateleira implements Comparable<Prateleira>{
    private final Localizacao_Armazenamento localizacao;
    private Palete palete;

    public Prateleira(Localizacao_Armazenamento localizacao) {
        this.localizacao = localizacao.clone();
        this.palete = null;
    }

    public Localizacao_Armazenamento getLocalizacao() {
        return localizacao;
    }

    public Palete getPalete() {
        return palete.clone();
    }

    public void inserePalete(Palete p) {
        this.palete = p.clone();
    }

    public Palete retiraPalete() {
        Palete p = this.palete.clone();
        this.palete = null;
        return p;
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public int compareTo(Prateleira p) {
        return this.localizacao.compareTo(p.getLocalizacao());
    }
}
