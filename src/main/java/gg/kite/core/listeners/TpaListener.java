package gg.kite.core.listeners;

import gg.kite.core.events.TpaRequestEvent;
import gg.kite.core.services.TeleportService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.slf4j.Logger;

public class TpaListener implements Listener {
    private static final Logger logger = LoggerFactory.getLogger(TpaListener.class);
    private final TeleportService teleportService;

    public TpaListener(TeleportService teleportService) {
        this.teleportService = teleportService;
    }

    @EventHandler
    public void onTpaRequest(TpaRequestEvent event) {
        if (!event.isCancelled()) {
            logger.info("TPA request from {} to {} processed", event.getSender().getName(), event.getTarget().getName());
        } else {
            logger.info("TPA request from {} to {} cancelled", event.getSender().getName(), event.getTarget().getName());
        }
    }
}