package gg.kite.core.exceptions;

import gg.kite.core.config.MessageConfig;

public class PlayerNotFoundException extends KiteException {
    public PlayerNotFoundException(String playerName, MessageConfig messageConfig) {
        super("player-not-found", messageConfig);
    }
}