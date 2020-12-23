package dss_project_fase3.business.Exceptions;

import dss_project_fase3.business.Enums.RobotRequest;

public class InvalidTransportOrderException extends Exception {
    int id_exc;

    /**
     * Função que emite a Exception de ordem de transporte inválido, depenendo do tipo do argumento que recebemos
     * @param id_exc     Parametro que mostra se a exception é por armazenamento ou não ter robots
     */
    public InvalidTransportOrderException(int id_exc) {
        this.id_exc = id_exc;
    }

    /**
     * Função que transforma a Exception feita numa String
     * @return           String resultante da função
     */
    public String getMessage() {
        String str = "";
        if (id_exc == 0) str = "Armazenamento encontra-se Cheio!";
        else if (id_exc == 1) str = "Não existem Robots disponíveis neste momento!";

        return str;
    }
}