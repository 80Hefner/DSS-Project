package dss_project_fase3.utils.Exceptions;

/**
 * Classe com Uma Exception de Código QR Inválido
 */
public class InvalidQRCodeException extends Exception {

    /**
     * Função que emite a Exception de Código QR Inválido
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
