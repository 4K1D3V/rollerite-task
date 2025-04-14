package gg.kite.core.exceptions;

import gg.kite.core.config.MessageConfig;

public class KiteException extends Exception {
    private final String messageKey;
    private final MessageConfig messageConfig;

    public KiteException(String messageKey, MessageConfig messageConfig) {
        this.messageKey = messageKey;
        this.messageConfig = messageConfig;
    }

    public KiteException(String message) {
        this.messageKey = null;
        this.messageConfig = null;
        super.initCause(new Throwable(message));
    }

    @Override
    public String getMessage() {
        return messageKey != null ? messageConfig.getMessage(messageKey) : super.getMessage();
    }
}