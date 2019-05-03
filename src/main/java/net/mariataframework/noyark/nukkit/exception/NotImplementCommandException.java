package net.mariataframework.noyark.nukkit.exception;

public class NotImplementCommandException extends ImplementException {

    public NotImplementCommandException() {
    }

    public NotImplementCommandException(String message) {
        super(message);
    }

    public NotImplementCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementCommandException(Throwable cause) {
        super(cause);
    }

    public NotImplementCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
