package dss_project_fase3.business;

import dss_project_fase3.business.Palete.Palete;
import dss_project_fase3.business.Palete.QR_Code;

import java.util.List;

public interface IArmazemFacade {

    void comunicar_codigo_qr(QR_Code qr_code);

    void comunicar_ordem_transporte();

    void notificar_recolha_palete(QR_Code qr_code, int id_robot);

    void notificar_entrega_palete(QR_Code qr_code, int id_robot);

    List<Palete> consultar_listagem_localizacoes();
}
