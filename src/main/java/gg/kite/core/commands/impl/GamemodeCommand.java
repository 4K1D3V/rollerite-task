package gg.kite.core.commands.impl;

import gg.kite.core.exceptions.InvalidArgumentException;
import gg.kite.core.exceptions.KiteException;
import gg.kite.core.exceptions.PlayerNotFoundException;
import gg.kite.core.services.PlayerService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.Map;

public class GamemodeCommand implements CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GamemodeCommand.class);
    private final PlayerService playerService;

    public GamemodeCommand(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (!sender.hasPermission("kite.gamemode")) {
                throw new KiteException("no-permission");
            }

            if (args.length < 1) {
                throw new InvalidArgumentException("&cUsage: /gamemode <type> [player]");
            }

            GameMode mode = parseGameMode(args[0]);
            Player target = args.length >= 2 ? playerService.getPlayer(args[1])
                    : playerService.getPlayerFromSender(sender);

            playerService.setGameMode(target, mode);
            sender.sendMessage(playerService.formatMessage("gamemode-success",
                    Map.of("player", target.getName(), "gamemode", mode.name().toLowerCase())));
            logger.info("Gamemode of {} set to {} by {}", target.getName(), mode, sender.getName());
            return true;
        } catch (KiteException e) {
            sender.sendMessage(e.getMessage());
            if (!(e instanceof InvalidArgumentException)) {
                logger.error("Error executing /gamemode", e);
            }
            return true;
        }
    }

    private GameMode parseGameMode(String input) throws InvalidArgumentException {
        try {
            return GameMode.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException("invalid-gamemode");
        }
    }
}