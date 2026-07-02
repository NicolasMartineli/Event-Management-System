package exceptions;

public class InvalidEventException extends RuntimeException {
    public InvalidEventException(String msg) {
        super(msg);
    }
}
