package in.rishank.investmentplatform.common.exception;

public class NavFetchException extends RuntimeException {

    public NavFetchException(String message) {
        super(message);
    }

    public NavFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}