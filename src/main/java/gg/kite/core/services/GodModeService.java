package gg.kite.core.services;

import gg.kite.core.config.MessageConfig;
import gg.kite.core.exceptions.KiteException;
import gg.kite.core.exceptions.PlayerNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GodModeService {
    private final MessageConfig messageConfig;
    private final Set<Player> godModePlayers = new CopyOnWriteArraySet<>();

    public GodModeService(MessageConfig messageConfig) {
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

    public boolean toggleGodMode(Player player) {
        if (godModePlayers.contains(player)) {
            godModePlayers.remove(player);
            return false;
        } else {
            godModePlayers.add(player);
            return true;
        }
    }

    public boolean isGodMode(Player player) {
        return godModePlayers.contains(player);
    }

    public String formatMessage(String key, Map<String, String> placeholders) {
        return messageConfig.getMessage(key, placeholders);
    }
}