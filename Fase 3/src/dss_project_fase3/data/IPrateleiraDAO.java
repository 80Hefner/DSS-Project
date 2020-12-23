package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Prateleira;

import java.util.Set;

public interface IPrateleiraDAO extends Set<Prateleira> {
    void inserePalete(Localizacao_Armazenamento localizacao, String qr_code);
    void removePalete(Localizacao_Armazenamento localizacao);
}
