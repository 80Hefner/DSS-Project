package dss_project_fase3.utils.Exceptions;

import dss_project_fase3.utils.Enums.TransportOrderError;

/**
 * Classe com Uma Exception de uma Ordem de Transporte inválida
 */
public class InvalidTransportOrderException extends Exception {

    TransportOrderError id;

    /**
     * Função que emite a Exception de ordem de transporte inválido, depenendo do tipo do argumento que recebemos
     * @param id     Parametro que mostra se a exception é por armazenamento ou não ter robots
     */
    public InvalidTransportOrderException(TransportOrderError id) {
        this.id = id;
    }

    /**
     * Função que transforma a Exception feita numa String
     * @return           String resultante da função
     */
    public String getMessage() {
        String str = "";
        if (id == TransportOrderError.ARMAZEM_CHEIO) str = "Armazenamento encontra-se Cheio!";
        else if (id == TransportOrderError.ROBOTS_INDISPONIVEIS) str = "Não existem Robots disponíveis neste momento!";

        return str;
    }
}