package dss_project_fase3.business.Exceptions;

/**
 * Classe com Uma Excepetion de Código QR Inválivo
 */
public class InvalidQRCodeException extends Exception {

    /**
     * Função que emite a Exception de Código QR Inválivo
     */
    public InvalidQRCodeException() {
        super("Código QR inválido!");
    }

    /**
     * Função que transforma a Exception feita numa String
     * @return           String resultante da função
     */
    public String getMessage() {
        return super.getMessage();
    }
}
