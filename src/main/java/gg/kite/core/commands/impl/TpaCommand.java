package gg.kite.core.commands.impl;

import gg.kite.core.exceptions.KiteException;
import gg.kite.core.services.TeleportService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Map;

public class TpaCommand implements CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TpaCommand.class);
    private final TeleportService teleportService;

    public TpaCommand(TeleportService teleportService) {
        this.teleportService = teleportService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!(sender instanceof Player player)) {
                throw new KiteException("&cThis command can only be used by players.");
            }

            if (!sender.hasPermission("kite.tpa")) {
                throw new KiteException("no-permission");
            }

            if (label.equalsIgnoreCase("tpa")) {
                if (args.length < 1) {
                    throw new KiteException("&cUsage: /tpa <player>");
                }

                Player target = teleportService.getPlayer(args[0]);
                if (target.equals(player)) {
                    throw new KiteException("&cYou cannot send a TPA request to yourself.");
                }

                teleportService.sendTpaRequest(player, target);
                player.sendMessage(teleportService.formatMessage("tpa-sent",
                        Map.of("player", target.getName())));
                target.sendMessage(teleportService.formatMessage("tpa-received",
                        Map.of("player", player.getName())));
                logger.info("{} sent TPA request to {}", player.getName(), target.getName());
            } else if (label.equalsIgnoreCase("tpaccept")) {
                teleportService.acceptTpaRequest(player);
                player.sendMessage(teleportService.formatMessage("tpa-accepted"));
                logger.info("{} accepted a TPA request", player.getName());
            } else if (label.equalsIgnoreCase("tpdeny")) {
                teleportService.denyTpaRequest(player);
                player.sendMessage(teleportService.formatMessage("tpa-denied"));
                logger.info("{} denied a TPA request", player.getName());
            }

            return true;
        } catch (KiteException e) {
            sender.sendMessage(e.getMessage());
            logger.error("Error executing /{}", label, e);
            return true;
        }
    }
}