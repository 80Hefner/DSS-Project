package dss_project_fase3.utils.Exceptions;

/**
 * Classe com Uma Exception de Não haver paletes para transportar
 */
public class EmptyTransportQueueException extends Exception {

    /**
     * Função que emite a Exception de que não há paletes para transportar
     */
    public EmptyTransportQueueException() {
        super("Não há paletes para serem transportadas!");
    }

    /**
     * Função que transforma a Exception feita numa String
     * @return           String resultante da função
     */
    public String getMessage() {
        return super.getMessage();
    }
}