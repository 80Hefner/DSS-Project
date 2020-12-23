package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Prateleira.Prateleira;

import java.util.Set;

public interface IPrateleiraDAO extends Set<Prateleira> {

    /**
     * Método que insere a referência de uma palete numa prateleira na base de dados
     * @param localizacao localizacao da prateleira em questão
     * @param qr_code QR_Code da palete em questão
     * @throws NullPointerException     Em caso de erro
     */
    void inserePalete(Localizacao_Armazenamento localizacao, String qr_code);

    /**
     * Método que remove a referência da palete numa prateleira na base de dados
     * @param localizacao localizacao da prateleira em questão
     * @throws NullPointerException     Em caso de erro
     */
    void removePalete(Localizacao_Armazenamento localizacao);
}
