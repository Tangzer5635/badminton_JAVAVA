package net.ent.etnc.badminton.shell.exceptions;

public class ShellException extends RuntimeException {
    public ShellException(String message) {
        super(message);
    }

    public ShellException(String message, Throwable cause) {
        super(message, cause);
    }
}
