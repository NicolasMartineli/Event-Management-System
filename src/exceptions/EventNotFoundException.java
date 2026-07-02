package exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String msg) {
        super(msg);
    }
}
