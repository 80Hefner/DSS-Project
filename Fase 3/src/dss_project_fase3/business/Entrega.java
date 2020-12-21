package dss_project_fase3.business;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Palete.Palete;

public class Entrega {
    private Palete palete;
    private Localizacao origem;
    private Localizacao destino;

    public Entrega(Palete palete, Localizacao origem, Localizacao destino) {
        this.palete = palete;
        this.origem = origem;
        this.destino = destino;
    }

    public Entrega(Entrega e) {
        this.palete = e.getPalete();
        this.origem = e.getOrigem();
        this.destino = e.getDestino();
    }

    public Palete getPalete() {
        return palete.clone();
    }

    public void setPalete(Palete palete) {
        this.palete = palete.clone();
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

        return this.palete.equals(e.getPalete()) &&
                this.origem.equals(e.getOrigem()) &&
                this.destino.equals(e.getDestino());
    }

    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Entrega: ").append("\n");
        sb.append(this.palete.toString(offset + "\t"));
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
