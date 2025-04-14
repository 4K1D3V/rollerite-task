package gg.kite.core.exceptions;

import gg.kite.core.config.MessageConfig;

public class InvalidArgumentException extends KiteException {
    public InvalidArgumentException(String messageKey, MessageConfig messageConfig) {
        super(messageKey, messageConfig);
    }

    public InvalidArgumentException(String message) {
        super(message);
    }
}