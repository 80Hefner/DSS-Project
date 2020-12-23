package dss_project_fase3.business.Exceptions;


public class EmptyTransportQueueException extends Exception {

    public EmptyTransportQueueException() {
        super("Não há paletes para serem transportadas!");
    }

    public String getMessage() {
        return super.getMessage();
    }
}