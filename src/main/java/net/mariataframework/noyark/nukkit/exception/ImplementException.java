package net.mariataframework.noyark.nukkit.exception;

public class ImplementException extends RuntimeException {
    public ImplementException() {
    }

    public ImplementException(String message) {
        super(message);
    }

    public ImplementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImplementException(Throwable cause) {
        super(cause);
    }

    public ImplementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
