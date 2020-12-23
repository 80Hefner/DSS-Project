package dss_project_fase3.business.Exceptions;

import dss_project_fase3.business.Enums.RobotRequest;

public class InvalidRequestFromRobot extends Exception {
    RobotRequest id;

    public InvalidRequestFromRobot(RobotRequest id) {
        this.id = id;
    }

    public String getMessage() {
        String str = "";
        if (id == RobotRequest.RECOLHA) str = "Pedido de recolha de palete inválido!";
        else if (id == RobotRequest.ENTREGA) str = "Pedido de entrega de palete inválido!";

        return str;
    }
}