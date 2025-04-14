package gg.kite.core.commands.impl;

import gg.kite.core.exceptions.KiteException;
import gg.kite.core.services.GodModeService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Map;

public class GodCommand implements CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GodCommand.class);
    private final GodModeService godModeService;

    public GodCommand(GodModeService godModeService) {
        this.godModeService = godModeService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!sender.hasPermission("kite.god")) {
                throw new KiteException("no-permission");
            }

            Player target = args.length >= 1 ? godModeService.getPlayer(args[0])
                    : godModeService.getPlayerFromSender(sender);

            boolean isGod = godModeService.toggleGodMode(target);
            sender.sendMessage(godModeService.formatMessage(isGod ? "god-enabled" : "god-disabled",
                    Map.of("player", target.getName())));
            logger.info("God mode {} for {} by {}", isGod ? "enabled" : "disabled", target.getName(), sender.getName());
            return true;
        } catch (KiteException e) {
            sender.sendMessage(e.getMessage());
            logger.error("Error executing /god", e);
            return true;
        }
    }
}