package dss_project_fase3.business;

public class Material {
    private String designacao;

    public Material(String designacao) {
        this.designacao = designacao;
    }

    public Material(Material m) {
        this.designacao = m.getDesignacao();
    }

    public String getDesignacao() {
        return this.designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Material m = (Material) o;

        return this.designacao.equals(m.getDesignacao());
    }

    public Material clone() {
        return new Material(this);
    }
}
