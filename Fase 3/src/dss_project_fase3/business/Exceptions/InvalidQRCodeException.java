package dss_project_fase3.business.Exceptions;

public class InvalidQRCodeException extends Exception{

    public InvalidQRCodeException() {
        super("Código QR inválido!");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
