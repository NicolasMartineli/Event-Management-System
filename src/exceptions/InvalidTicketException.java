package exceptions;

public class InvalidTicketException extends RuntimeException {
    public InvalidTicketException(String msg) {
        super(msg);
    }
}
