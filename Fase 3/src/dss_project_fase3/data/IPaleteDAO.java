package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Palete.Palete;

import java.util.Map;

public interface IPaleteDAO extends Map<String, Palete> {

    void atualizaLocalizacao(Localizacao localizacao, String qr_code);
}
