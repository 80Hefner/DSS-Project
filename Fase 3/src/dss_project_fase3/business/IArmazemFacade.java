package dss_project_fase3.business;

import dss_project_fase3.utils.Exceptions.EmptyTransportQueueException;
import dss_project_fase3.utils.Exceptions.InvalidRequestFromRobot;
import dss_project_fase3.utils.Exceptions.InvalidRobotIDException;
import dss_project_fase3.utils.Exceptions.InvalidTransportOrderException;
import dss_project_fase3.business.Palete.Palete;
import dss_project_fase3.business.Palete.QR_Code;

import java.util.List;

/**
 * Interface do ArmazemFacade
 */
public interface IArmazemFacade {

    /**
     * Função responsável por comunicar um Código QR ao Sistema
     * @param qr_code       QR_Code a ser comunicado
     */
    void comunicar_codigo_qr(QR_Code qr_code);

    /**
     * Função responsável por comunicar uma ordem de transporte
     * @throws EmptyTransportQueueException     Excepção de quando não existem paletes para serem transportadas
     * @throws InvalidTransportOrderException   Exceção de quando não existe armazenamento disponível ou robots disponíveis
     */
    void comunicar_ordem_transporte() throws EmptyTransportQueueException, InvalidTransportOrderException;

    /**
     * Função responsável por comunicar recolha da Palete por parte do Robot
     * @param id_robot                      id do Robot que recolhe a palete
     * @throws InvalidRobotIDException      Excepção de quando id do robot é inválido
     * @throws InvalidRequestFromRobot      Exceção de quando recolha não pode ser efetuada
     */
    public void notificar_recolha_palete(int id_robot) throws InvalidRobotIDException, InvalidRequestFromRobot;

    /**
     * Função responsável por comunicar entrega da Palete por parte do Robot
     * @param id_robot                      id do Robot que recolhe a palete
     * @throws InvalidRobotIDException      Excepção de quando id do robot é inválido
     * @throws InvalidRequestFromRobot      Exceção de quando entrega não pode ser efetuada
     */
    void notificar_entrega_palete(int id_robot) throws InvalidRobotIDException, InvalidRequestFromRobot;

    /**
     * Função que recolhe a listagem de todas as paletes no Sistema
     * @return      List com todas as Paletes do Sistema
     */
    List<Palete> consultar_listagem_localizacoes();
}
