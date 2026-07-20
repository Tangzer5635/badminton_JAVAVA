package net.ent.etnc.badminton.models.facades.exceptions;

public class FacadeMetierException extends Exception {
    public FacadeMetierException(String message) {
        super(message);
    }

    public FacadeMetierException(String message, Throwable cause) {
        super(message, cause);
    }
}
