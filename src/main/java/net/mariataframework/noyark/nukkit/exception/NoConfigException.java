package net.mariataframework.noyark.nukkit.exception;

public class NoConfigException extends RuntimeException{

    public NoConfigException() {
    }

    public NoConfigException(String message) {
        super(message);
    }

    public NoConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoConfigException(Throwable cause) {
        super(cause);
    }

    public NoConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
