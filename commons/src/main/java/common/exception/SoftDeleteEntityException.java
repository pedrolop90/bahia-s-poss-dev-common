package common.exception;

public class SoftDeleteEntityException extends RuntimeException {
    public SoftDeleteEntityException(String message) {
        super(message);
    }
}
