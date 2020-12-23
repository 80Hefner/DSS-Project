package dss_project_fase3.business;

import dss_project_fase3.business.Exceptions.EmptyTransportQueueException;
import dss_project_fase3.business.Exceptions.InvalidRequestFromRobot;
import dss_project_fase3.business.Exceptions.InvalidRobotIDException;
import dss_project_fase3.business.Palete.Palete;
import dss_project_fase3.business.Palete.QR_Code;

import java.util.List;

public interface IArmazemFacade {

    void comunicar_codigo_qr(QR_Code qr_code);

    void comunicar_ordem_transporte() throws EmptyTransportQueueException;

    public void notificar_recolha_palete(int id_robot) throws InvalidRobotIDException, InvalidRequestFromRobot;

    void notificar_entrega_palete(int id_robot) throws InvalidRobotIDException, InvalidRequestFromRobot;

    List<Palete> consultar_listagem_localizacoes();
}
