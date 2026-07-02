package exceptions;

public class EventFullException extends RuntimeException {
    public EventFullException(String msg) {
        super(msg);
    }
}
