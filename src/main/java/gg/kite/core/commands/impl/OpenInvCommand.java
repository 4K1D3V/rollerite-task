package gg.kite.core.commands.impl;

import gg.kite.core.exceptions.KiteException;
import gg.kite.core.services.InventoryService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Map;

public class OpenInvCommand implements CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(OpenInvCommand.class);
    private final InventoryService inventoryService;

    public OpenInvCommand(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!(sender instanceof Player player)) {
                throw new KiteException("&cThis command can only be used by players.");
            }

            if (!sender.hasPermission("kite.openinv")) {
                throw new KiteException("no-permission");
            }

            if (args.length < 1) {
                throw new KiteException("&cUsage: /openinv <player>");
            }

            Player target = inventoryService.getPlayer(args[0]);
            inventoryService.openInventory(player, target.getInventory());
            player.sendMessage(inventoryService.formatMessage("openinv-success",
                    Map.of("player", target.getName())));
            logger.info("{} opened inventory of {}", sender.getName(), target.getName());
            return true;
        } catch (KiteException e) {
            sender.sendMessage(e.getMessage());
            logger.error("Error executing /openinv", e);
            return true;
        }
    }
}