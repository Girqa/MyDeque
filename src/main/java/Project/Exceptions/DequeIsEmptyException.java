package Project.Exceptions;

public class DequeIsEmptyException extends RuntimeException {

    public DequeIsEmptyException() {
    }

    public DequeIsEmptyException(String message) {
        super(message);
    }
}
