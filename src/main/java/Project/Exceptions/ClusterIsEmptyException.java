package Project.Exceptions;

public class ClusterIsEmptyException extends RuntimeException {
    public ClusterIsEmptyException() {
    }

    public ClusterIsEmptyException(String message) {
        super(message);
    }
}
