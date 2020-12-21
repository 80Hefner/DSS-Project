package dss_project_fase3.business.Localizacao;

import dss_project_fase3.business.Enums.ZonaArmazem;

public class Localizacao_Armazenamento extends Localizacao implements Comparable<Localizacao_Armazenamento>{
    private int corredor;
    private int setor;

    public Localizacao_Armazenamento(int corredor, int setor) {
        super(ZonaArmazem.ZONA_ARMAZENAMENTO);
        this.corredor = corredor;
        this.setor = setor;
    }

    public Localizacao_Armazenamento(Localizacao_Armazenamento l) {
        super(l.getZona());
        this.corredor = l.getCorredor();
        this.setor = l.getSetor();
    }

    public int getCorredor() {
        return corredor;
    }

    public void setCorredor(int corredor) {
        this.corredor = corredor;
    }

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Localizacao_Armazenamento l = (Localizacao_Armazenamento) o;

        return super.getZona().equals(l.getZona()) &&
                this.corredor == l.getCorredor() &&
                this.setor == l.getSetor();
    }

    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Localização: Armazém\n");
        sb.append(offset).append("\tCorredor: ").append(this.corredor).append("\n");
        sb.append(offset).append("\tSetor: ").append(this.setor).append("\n");

        return sb.toString();
    }

    public Localizacao_Armazenamento clone() {
        return new Localizacao_Armazenamento(this);
    }

    public int compareTo(Localizacao_Armazenamento l) {
        int res = this.corredor - l.getCorredor();
        if (res == 0) res = this.setor - l.getSetor();
        return res;
    }
}