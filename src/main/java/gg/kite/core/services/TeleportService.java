package gg.kite.core.services;

import gg.kite.core.config.ConfigManager;
import gg.kite.core.config.MessageConfig;
import gg.kite.core.events.TpaRequestEvent;
import gg.kite.core.exceptions.KiteException;
import gg.kite.core.exceptions.PlayerNotFoundException;
import gg.kite.core.utils.LoggerFactory;
import gg.kite.core.utils.SchedulerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class TeleportService {
    private static final Logger logger = LoggerFactory.getLogger(TeleportService.class);
    private final MessageConfig messageConfig;
    private final ConfigManager configManager;
    private final Map<Player, Player> pendingRequests = new ConcurrentHashMap<>();

    public TeleportService(MessageConfig messageConfig, ConfigManager configManager) {
        this.messageConfig = messageConfig;
        this.configManager = configManager;
    }

    public Player getPlayer(String name) throws PlayerNotFoundException {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            throw new PlayerNotFoundException(name, messageConfig);
        }
        return player;
    }

    public void sendTpaRequest(Player sender, Player target) {
        TpaRequestEvent event = new TpaRequestEvent(sender, target);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        pendingRequests.put(target, sender);
        SchedulerUtil.runTaskLater(() -> {
            if (pendingRequests.remove(target, sender)) {
                sender.sendMessage(formatMessage("tpa-expired", Map.of("player", target.getName())));
                if (target.isOnline()) {
                    target.sendMessage(formatMessage("tpa-expired", Map.of("player", sender.getName())));
                }
                logger.info("TPA request from {} to {} expired", sender.getName(), target.getName());
            }
        }, configManager.getTpaTimeout(), TimeUnit.SECONDS);
    }

    public void acceptTpaRequest(Player player) throws KiteException {
        Player sender = pendingRequests.remove(player);
        if (sender == null) {
            throw new KiteException("tpa-no-request", messageConfig);
        }
        if (sender.isOnline()) {
            sender.teleport(player);
            sender.sendMessage(formatMessage("tpa-accepted"));
        }
    }

    public void denyTpaRequest(Player player) throws KiteException {
        Player sender = pendingRequests.remove(player);
        if (sender == null) {
            throw new KiteException("tpa-no-request", messageConfig);
        }
        if (sender.isOnline()) {
            sender.sendMessage(formatMessage("tpa-denied"));
        }
    }

    public void clearRequests() {
        pendingRequests.clear();
    }

    public String formatMessage(String key, Map<String, String> placeholders) {
        return messageConfig.getMessage(key, placeholders);
    }

    public String formatMessage(String key) {
        return messageConfig.getMessage(key);
    }
}