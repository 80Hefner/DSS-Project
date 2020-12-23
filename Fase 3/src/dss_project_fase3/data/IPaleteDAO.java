package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Palete.Palete;

import java.util.Map;

/**
 * Interface IPalateDAO que dá exntend ao Map<String, Palete>
 */
public interface IPaleteDAO extends Map<String, Palete> {

    /**
     * Função que atualiza localizacao de uma dada palete, dado um qr_code
     * @param localizacao       localizacao da Palete
     * @param qr_code           qr_code da Palete
     * @throws NullPointerException     Em caso de erro
     */
    void atualizaLocalizacao(Localizacao localizacao, String qr_code);
}
