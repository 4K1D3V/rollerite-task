package gg.kite.core.commands.impl;

import gg.kite.core.exceptions.KiteException;
import gg.kite.core.services.InventoryService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

public class FixCommand implements CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FixCommand.class);
    private final InventoryService inventoryService;

    public FixCommand(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!(sender instanceof Player player)) {
                throw new KiteException("&cThis command can only be used by players.");
            }

            if (!sender.hasPermission("kite.fix")) {
                throw new KiteException("no-permission");
            }

            inventoryService.repairItem(player);
            player.sendMessage(inventoryService.formatMessage("fix-success"));
            logger.info("{} repaired their item", player.getName());
            return true;
        } catch (KiteException e) {
            sender.sendMessage(e.getMessage());
            logger.error("Error executing /fix", e);
            return true;
        }
    }
}