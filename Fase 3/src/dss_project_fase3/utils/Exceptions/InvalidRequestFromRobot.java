package dss_project_fase3.utils.Exceptions;

import dss_project_fase3.utils.Enums.RobotRequest;

/**
 * Classe com Uma Exception de request inválido
 */
public class InvalidRequestFromRobot extends Exception {

    RobotRequest id;

    /**
     * Função que emite a Exception de request inválido, depenendo do tipo do argumento que recebemos
     * @param id     Parametro que mostra se a exception é por pedido de recolha ou pedido de entrega
     */
    public InvalidRequestFromRobot(RobotRequest id) {
        this.id = id;
    }

    /**
     * Função que transforma a Exception feita numa String
     * @return           String resultante da função
     */
    public String getMessage() {
        String str = "";
        if (id == RobotRequest.RECOLHA) str = "Pedido de recolha de palete inválido!";
        else if (id == RobotRequest.ENTREGA) str = "Pedido de entrega de palete inválido!";

        return str;
    }
}