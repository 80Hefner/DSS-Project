package dss_project_fase3.business.Palete;

/**
 * Classe Material
 */
public class Material {
    private String designacao;

    /**
     * Construtor parametrizado de Material
     * @param designacao           Desgignacao do Material
     */
    public Material(String designacao) {
        this.designacao = designacao;
    }

    /**
     * Construtor de cópia do Material
     * @param m           Material a copiar
     */
    public Material(Material m) {
        this.designacao = m.getDesignacao();
    }

    /**
     * Getter da Desgignacao do Material
     * @return           Desgignacao do Material
     */
    public String getDesignacao() {
        return this.designacao;
    }

    /**
     * Setter da Desgignacao do Material
     * @param designacao           Desgignacao do Material a colocar
     */
    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    /**
     * Função de equals de Material
     * @param o           Objeto ao qual queremos comparar o Material
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Material m = (Material) o;

        return this.designacao.equals(m.getDesignacao());
    }

    /**
     * Função que transforma o Material numa String
     * @return           String resultante da função
     */
    public String toString(String offset) { return offset + "Material: " + this.designacao + "\n"; }

    /**
     * Função que dá clone ao Material
     * @return           Cópia do Material
     */
    public Material clone() {
        return new Material(this);
    }
}
