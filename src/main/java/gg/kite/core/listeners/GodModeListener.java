package gg.kite.core.listeners;

import gg.kite.core.services.GodModeService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class GodModeListener implements Listener {
    private final GodModeService godModeService;

    public GodModeListener(GodModeService godModeService) {
        this.godModeService = godModeService;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && godModeService.isGodMode(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player && godModeService.isGodMode(player)) {
            event.setCancelled(true);
            player.setFoodLevel(20);
        }
    }
}