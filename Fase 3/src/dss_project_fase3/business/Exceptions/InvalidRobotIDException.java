package dss_project_fase3.business.Exceptions;

public class InvalidRobotIDException extends Exception {

    public InvalidRobotIDException() {
        super("ID do Robot Inválido!");
    }

    public String getMessage() {
        return super.getMessage();
    }
}
