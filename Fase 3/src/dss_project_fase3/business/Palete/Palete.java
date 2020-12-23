package dss_project_fase3.business.Palete;

import dss_project_fase3.business.Localizacao.*;
import static dss_project_fase3.utils.Enums.ZonaArmazem.*;

/**
 * Classe Palete
 */
public class Palete {
    private QR_Code qr_code;
    private Material material;
    private Localizacao localizacao;

    /**
     * Construtor parametrizado da Palete
     * @param qr_code           QR_Code associado a uma palete
     * @param material          Material associado a uma Palete
     * @param localizacao       Localização associada a uma palete
     */
    public Palete(QR_Code qr_code, Material material, Localizacao localizacao) {
        this.qr_code = qr_code;
        this.material = material;
        this.localizacao = localizacao;
    }

    /**
     * Construtor parametrizado da Palete
     * @param qr_code           Código QR_Code associado a uma palete
     * @param material          Designação do Material associado a uma Palete
     * @param localizacao       Designação da Localização associada a uma palete
     * @param corredor          corredor da palete
     * @param setor             setor da palete
     * @param id_robot          id_robot da palete
     */
    public Palete(String qr_code, String material, String localizacao, int corredor, int setor, int id_robot) {
        this.qr_code = new QR_Code(qr_code);
        this.material = new Material(material);
        switch (localizacao){
            case "ZONA_RECECAO":
                this.localizacao = new Localizacao_Transporte(ZONA_RECECAO);
                break;
            case "ZONA_ENTREGA":
                this.localizacao = new Localizacao_Transporte(ZONA_ENTREGA);
                break;
            case "ZONA_ARMAZENAMENTO":
                this.localizacao = new Localizacao_Armazenamento(corredor,setor);
                break;
            case "ROBOT":
                this.localizacao = new Localizacao_Robot(id_robot);
                break;
        }
    }

    /**
     * Construtor de cópia da Palete
     * @param p           Palete a copiar
     */
    public Palete(Palete p) {
        this.qr_code = p.getQr_code();
        this.material = p.getMaterial();
        this.localizacao = p.getLocalizacao();
    }

    /**
     * Getter do QR_Code da Palete
     * @return           QR_Code da Palete
     */
    public QR_Code getQr_code() {
        return qr_code;
    }

    /**
     * Setter do QR_Code da Palete
     * @param qr_code           QR_Code da Palete a colocar
     */
    public void setQr_code(QR_Code qr_code) {
        this.qr_code = qr_code;
    }

    /**
     * Getter do Material da Palete
     * @return           Material da Palete
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Setter do Material da Palete
     * @param material          Material da Palete a colocar
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Getter da Localizacao da Palete
     * @return           Localizacao da Palete
     */
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * Setter da Localizacao da Palete
     * @param localizacao           Localizacao da Palete a colocar
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Função de equals de Palete
     * @param o           Objeto ao qual queremos comparar a Palete
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Palete p = (Palete) o;

        return this.qr_code.equals(p.getQr_code()) &&
                this.material.equals(p.getMaterial()) &&
                this.localizacao.equals(p.getLocalizacao());
    }

    /**
     * Função que transforma a Palete numa String
     * @return           String resultante da função
     */
    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Palete: ").append(this.qr_code.getCodigo()).append("\n");
        sb.append(this.material.toString(offset + "\t"));
        sb.append(this.localizacao.toString(offset + "\t"));

        return sb.toString();
    }

    /**
     * Função que dá clone à Palete
     * @return           Cópia da Palete
     */
    public Palete clone() {
        return new Palete(this);
    }
}
