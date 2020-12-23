package dss_project_fase3.business.Prateleira;

import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Palete.Palete;

/**
 * Classe Prateleira que implementa o seu Comparable
 */
public class Prateleira implements Comparable<Prateleira>{
    private final Localizacao_Armazenamento localizacao;
    private String qr_code;

    /**
     * Construtor parametrizado de Prateleira
     * @param localizacao   Localizacao_Armazenamento da Prateleira
     */
    public Prateleira(Localizacao_Armazenamento localizacao) {
        this.localizacao = localizacao.clone();
        this.qr_code = null;
    }

    /**
     * Construtor parametrizado de Prateleira
     * @param localizacao   Localizacao_Armazenamento da Prateleira
     * @param qr_code       String com código de QR_Code
     */
    public Prateleira(Localizacao_Armazenamento localizacao, String qr_code) {
        this.localizacao = localizacao.clone();
        this.qr_code = qr_code;
    }

    /**
     * Getter da Localizacao_Armazenamento da Prateleira
     * @return           Localizacao_Armazenamento da Prateleira
     */
    public Localizacao_Armazenamento getLocalizacao() {
        return localizacao;
    }

    /**
     * Getter do código QR_Code associado a Prateleira
     * @return           Código QR_Code associado a Prateleira
     */
    public String getQr_code() {
        return qr_code;
    }

    /**
     * Função que faz as alterações necessárias no momento de introduzir uma palete na Prateleira
     * @param p     Palete a inserir na Prateleira
     */
    public void inserePalete(Palete p) {
        this.qr_code = p.getQr_code().getCodigo();
    }

    /**
     * Função que faz as alterações necessárias no momento de retirar uma palete da Prateleira
     * @return      String com código de QR_Code associado à Palete retirada
     */
    public String retiraPalete() {
        String str = this.qr_code;
        this.qr_code = null;
        return str;
    }

    /**
     * Função de equals de Prateleira
     * @param o           Objeto ao qual queremos comparar a Prateleira
     */
    public boolean equals(Object o) {
        return this == o;
    }

    /**
     * Função que faz a comparação Natural a ser usada pela Prateleira sempre
     * @param p    Prateleira ao qual queremos comparar o this
     * @return     Inteiro que vai servir de comparação
     */
    public int compareTo(Prateleira p) {
        return this.localizacao.compareTo(p.getLocalizacao());
    }
}
