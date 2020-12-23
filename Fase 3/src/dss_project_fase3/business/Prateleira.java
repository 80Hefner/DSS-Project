package dss_project_fase3.business;

import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Palete.Palete;

public class Prateleira implements Comparable<Prateleira>{
    private final Localizacao_Armazenamento localizacao;
    private String qr_code;

    public Prateleira(Localizacao_Armazenamento localizacao) {
        this.localizacao = localizacao.clone();
        this.qr_code = null;
    }

    public Prateleira(Localizacao_Armazenamento localizacao, String qr_code) {
        this.localizacao = localizacao.clone();
        this.qr_code = qr_code;
    }

    public Localizacao_Armazenamento getLocalizacao() {
        return localizacao;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void inserePalete(Palete p) {
        this.qr_code = p.getQr_code().getCodigo();
    }

    public String retiraPalete() {
        String str = this.qr_code;
        this.qr_code = null;
        return str;
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public int compareTo(Prateleira p) {
        return this.localizacao.compareTo(p.getLocalizacao());
    }
}
