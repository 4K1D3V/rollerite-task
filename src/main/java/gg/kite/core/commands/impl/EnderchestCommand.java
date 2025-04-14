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

public class EnderchestCommand implements CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(EnderchestCommand.class);
    private final InventoryService inventoryService;

    public EnderchestCommand(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!sender.hasPermission("kite.enderchest")) {
                throw new KiteException("no-permission");
            }

            Player target = args.length >= 1 ? inventoryService.getPlayer(args[0])
                    : inventoryService.getPlayerFromSender(sender);

            if (sender instanceof Player player) {
                inventoryService.openInventory(player, target.getEnderChest());
            }
            sender.sendMessage(inventoryService.formatMessage("enderchest-success",
                    Map.of("player", target.getName())));
            logger.info("{} opened ender chest of {}", sender.getName(), target.getName());
            return true;
        } catch (KiteException e) {
            sender.sendMessage(e.getMessage());
            logger.error("Error executing /enderchest", e);
            return true;
        }
    }
}