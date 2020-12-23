package dss_project_fase3.utils.Exceptions;

/**
 * Classe com Uma Exception de ID de Robot Inválido
 */
public class InvalidRobotIDException extends Exception {

    /**
     * Função que emite a Exception de ID de Robot Inválido
     */
    public InvalidRobotIDException() {
        super("ID do Robot Inválido!");
    }

    /**
     * Função que transforma a Exception feita numa String
     * @return           String resultante da função
     */
    public String getMessage() {
        return super.getMessage();
    }
}
