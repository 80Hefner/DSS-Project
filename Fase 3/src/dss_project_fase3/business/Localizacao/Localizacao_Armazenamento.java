package dss_project_fase3.business.Localizacao;

import dss_project_fase3.utils.Enums.ZonaArmazem;

/**
 * Classe Localizacao_Armazenamento que estende a classe abestrata Localizacao e implementa o seu Comparable
 */
public class Localizacao_Armazenamento extends Localizacao implements Comparable<Localizacao_Armazenamento>{
    private int corredor;
    private int setor;

    /**
     * Construtor parametrizado da Localizacao_Armazenamento
     * @param corredor           Corredor
     * @param setor              Setor
     */
    public Localizacao_Armazenamento(int corredor, int setor) {
        super(ZonaArmazem.ZONA_ARMAZENAMENTO);
        this.corredor = corredor;
        this.setor = setor;
    }

    /**
     * Construtor de cópia da Localizacao_Armazenamento
     * @param l           Localizacao_Armazenamento a copiar
     */
    public Localizacao_Armazenamento(Localizacao_Armazenamento l) {
        super(l.getZona());
        this.corredor = l.getCorredor();
        this.setor = l.getSetor();
    }

    /**
     * Getter do Corredor da Localizacao_Armazenamento
     * @return           Corredor da Localizacao_Armazenamento
     */
    public int getCorredor() {
        return corredor;
    }

    /**
     * Setter do Corredor da Localizacao_Armazenamento
     * @param corredor           Corredor da Localizacao_Armazenamento
     */
    public void setCorredor(int corredor) {
        this.corredor = corredor;
    }

    /**
     * Getter do Setor da Localizacao_Armazenamento
     * @return           Setor da Localizacao_Armazenamento
     */
    public int getSetor() {
        return setor;
    }

    /**
     * Setter do Setor da Localizacao_Armazenamento
     * @param setor           Setor da Localizacao_Armazenamento
     */
    public void setSetor(int setor) {
        this.setor = setor;
    }

    /**
     * Função de equals da Localizacao_Armazenamento
     * @param o           Objeto ao qual queremos comparar a Localizacao_Armazenamento
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Localizacao_Armazenamento l = (Localizacao_Armazenamento) o;

        return super.getZona().equals(l.getZona()) &&
                this.corredor == l.getCorredor() &&
                this.setor == l.getSetor();
    }

    /**
     * Função que transforma a Localizacao_Armazenamento numa String
     * @return           String resultante da função
     */
    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Localização: Armazém\n");
        sb.append(offset).append("\tCorredor: ").append(this.corredor).append("\n");
        sb.append(offset).append("\tSetor: ").append(this.setor).append("\n");

        return sb.toString();
    }

    /**
     * Função que dá clone à Localizacao_Armazenamento
     * @return           Cópia da Localizacao_Armazenamento
     */
    public Localizacao_Armazenamento clone() {
        return new Localizacao_Armazenamento(this);
    }

    /**
     * Função que faz a comparação Natural a ser usada pela Localizacao_Armazenamento sempre
     * @param l    Localizacao_Armazenamento ao qual queremos comparar o this
     * @return     Inteiro que vai servir de comparação
     */
    public int compareTo(Localizacao_Armazenamento l) {
        int res = this.corredor - l.getCorredor();
        if (res == 0) res = this.setor - l.getSetor();
        return res;
    }
}