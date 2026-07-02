package exceptions;

public class DuplicateBuyerException extends RuntimeException {
    public DuplicateBuyerException(String msg) {
        super(msg);
    }
}
