package gg.kite.core.services;

import gg.kite.core.config.MessageConfig;
import gg.kite.core.exceptions.InvalidArgumentException;
import gg.kite.core.exceptions.KiteException;
import gg.kite.core.exceptions.PlayerNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlayerService {
    private final MessageConfig messageConfig;

    public PlayerService(MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    public Player getPlayer(String name) throws PlayerNotFoundException {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            throw new PlayerNotFoundException(name, messageConfig);
        }
        return player;
    }

    public Player getPlayerFromSender(CommandSender sender) throws KiteException {
        if (!(sender instanceof Player)) {
            throw new KiteException("console-player-required", messageConfig);
        }
        return (Player) sender;
    }

    public void setGameMode(Player player, GameMode mode) {
        player.setGameMode(mode);
    }

    public String formatMessage(String key, Map<String, String> placeholders) {
        return messageConfig.getMessage(key, placeholders);
    }
}