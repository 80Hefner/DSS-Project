package dss_project_fase3.business;

import dss_project_fase3.business.Localizacao.Localizacao;

public class Entrega {
    private String qr_code;
    private Localizacao origem;
    private Localizacao destino;

    public Entrega(String qr_code, Localizacao origem, Localizacao destino) {
        this.qr_code = qr_code;
        this.origem = origem;
        this.destino = destino;
    }

    public Entrega(Entrega e) {
        this.qr_code = e.getQr_code();
        this.origem = e.getOrigem();
        this.destino = e.getDestino();
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public Localizacao getOrigem() {
        return origem.clone();
    }

    public void setOrigem(Localizacao origem) {
        this.origem = origem.clone();
    }

    public Localizacao getDestino() {
        return destino.clone();
    }

    public void setDestino(Localizacao destino) {
        this.destino = destino.clone();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Entrega e = (Entrega) o;

        return this.qr_code.equals(e.getQr_code()) &&
                this.origem.equals(e.getOrigem()) &&
                this.destino.equals(e.getDestino());
    }

    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Entrega: ").append("\n");
        sb.append(offset).append("Palete: ").append(this.qr_code).append("\n");
        sb.append(offset).append("Origem:").append("\n");
        sb.append(this.origem.toString(offset + "\t"));
        sb.append(offset).append("Destino:").append("\n");
        sb.append(this.destino.toString(offset + "\t"));

        return sb.toString();
    }

    public Entrega clone() {
        return new Entrega(this);
    }

}
