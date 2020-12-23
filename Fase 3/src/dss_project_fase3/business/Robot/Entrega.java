package dss_project_fase3.business.Robot;

import dss_project_fase3.business.Localizacao.*;
import static dss_project_fase3.utils.Enums.ZonaArmazem.*;

/**
 * Classe Entrega
 */
public class Entrega {
    private String qr_code;
    private Localizacao origem;
    private Localizacao destino;

    /**
     * Construtor parametrizado da Entrega
     * @param qr_code           QR_Code associado a uma Entrega
     * @param origem            Localização origem da Entrega
     * @param destino           Localização destino da Entrega
     */
    public Entrega(String qr_code, Localizacao origem, Localizacao destino) {
        this.qr_code = qr_code;
        this.origem = origem;
        this.destino = destino;
    }

    /**
     * Construtor de cópia da Entrega
     * @param e           Entrega a copiar
     */
    public Entrega(Entrega e) {
        this.qr_code = e.getQr_code();
        this.origem = e.getOrigem();
        this.destino = e.getDestino();
    }

    /**
     * Construtor parametrizado da Entrega
     * @param qr_code                   Código QR_Code associado a uma palete
     * @param localizacao_origem        Designação da Localização origem associada a uma Entrega
     * @param corredor_origem           Corredor origem da Entrega
     * @param setor_origem              Setor origem da Entrega
     * @param localizacao_destino       Designação da Localização destino associada a uma Entrega
     * @param corredor_destino          Corredor destino da Entrega
     * @param setor_destino             Setor destino da Entrega
     */
    public Entrega (String qr_code, String localizacao_origem, int corredor_origem, int setor_origem, String localizacao_destino, int corredor_destino, int setor_destino) {
        this.qr_code = qr_code;
        if (localizacao_origem != null) {
            switch (localizacao_origem){
                case "ZONA_RECECAO":
                    this.origem = new Localizacao_Transporte(ZONA_RECECAO);
                    break;
                case "ZONA_ENTREGA":
                    this.origem = new Localizacao_Transporte(ZONA_ENTREGA);
                    break;
                case "ZONA_ARMAZENAMENTO":
                    this.origem = new Localizacao_Armazenamento(corredor_origem,setor_origem);
                    break;
            }
        }

        if (localizacao_destino != null) {
            switch (localizacao_destino){
                case "ZONA_RECECAO":
                    this.destino = new Localizacao_Transporte(ZONA_RECECAO);
                    break;
                case "ZONA_ENTREGA":
                    this.destino = new Localizacao_Transporte(ZONA_ENTREGA);
                    break;
                case "ZONA_ARMAZENAMENTO":
                    this.destino = new Localizacao_Armazenamento(corredor_destino,setor_destino);
                    break;
            }
        }
    }

    /**
     * Getter do código QR_Code associado a uma Entrega
     * @return           Código QR_Code associado a uma Entrega
     */
    public String getQr_code() {
        return qr_code;
    }

    /**
     * Setter do código QR_Code associado a uma Entrega
     * @param qr_code           código QR_Code associado a uma Entrega a colocar
     */
    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    /**
     * Getter da Localizacao Origem associada a uma Entrega
     * @return           Localizacao Origem associada a uma Entrega
     */
    public Localizacao getOrigem() {
        return origem.clone();
    }

    /**
     * Setter da Localizacao Origem associada a uma Entrega
     * @param origem           Localizacao Origem associada a uma Entrega a colocar
     */
    public void setOrigem(Localizacao origem) {
        this.origem = origem.clone();
    }

    /**
     * Getter da Localizacao Destino associada a uma Entrega
     * @return           Localizacao Destino associada a uma Entrega
     */
    public Localizacao getDestino() {
        return destino.clone();
    }

    /**
     * Setter da Localizacao Destino associada a uma Entrega
     * @param destino           Localizacao Destino associada a uma Entrega a colocar
     */
    public void setDestino(Localizacao destino) {
        this.destino = destino.clone();
    }

    /**
     * Função de equals de Entrega
     * @param o           Objeto ao qual queremos comparar a Entrega
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Entrega e = (Entrega) o;

        return this.qr_code.equals(e.getQr_code()) &&
                this.origem.equals(e.getOrigem()) &&
                this.destino.equals(e.getDestino());
    }

    /**
     * Função que transforma a Entrega numa String
     * @return           String resultante da função
     */
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

    /**
     * Função que dá clone à Entrega
     * @return           Cópia da Entrega
     */
    public Entrega clone() {
        return new Entrega(this);
    }

}
