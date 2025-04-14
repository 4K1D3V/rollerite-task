package gg.kite.core.services;

import gg.kite.core.config.MessageConfig;
import gg.kite.core.exceptions.InvalidArgumentException;
import gg.kite.core.exceptions.KiteException;
import gg.kite.core.exceptions.PlayerNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryService {
    private final MessageConfig messageConfig;

    public InventoryService(MessageConfig messageConfig) {
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

    public void openInventory(Player player, Inventory inventory) {
        player.openInventory(inventory);
    }

    public void openTrashInventory(Player player) {
        Inventory trash = Bukkit.createInventory(player, 54, "Trash");
        player.openInventory(trash);
    }

    public void repairItem(Player player) throws KiteException {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            throw new KiteException("fix-no-item", messageConfig);
        }
        item.setDurability((short) 0);
    }

    public String formatMessage(String key, Map<String, String> placeholders) {
        return messageConfig.getMessage(key, placeholders);
    }

    public String formatMessage(String key) {
        return messageConfig.getMessage(key);
    }
}