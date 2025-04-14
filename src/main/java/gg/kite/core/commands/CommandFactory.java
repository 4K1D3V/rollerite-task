package gg.kite.core.commands;

import gg.kite.core.commands.impl.*;
import gg.kite.core.services.GodModeService;
import gg.kite.core.services.InventoryService;
import gg.kite.core.services.PlayerService;
import gg.kite.core.services.TeleportService;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final JavaPlugin plugin;
    private final PlayerService playerService;
    private final InventoryService inventoryService;
    private final GodModeService godModeService;
    private final TeleportService teleportService;

    public CommandFactory(JavaPlugin plugin, PlayerService playerService, InventoryService inventoryService,
                          GodModeService godModeService, TeleportService teleportService) {
        this.plugin = plugin;
        this.playerService = playerService;
        this.inventoryService = inventoryService;
        this.godModeService = godModeService;
        this.teleportService = teleportService;
    }

    public void registerCommands() {
        Map<String, CommandExecutor> commands = new HashMap<>();
        commands.put("gamemode", new GamemodeCommand(playerService));
        commands.put("god", new GodCommand(godModeService));
        commands.put("openinv", new OpenInvCommand(inventoryService));
        commands.put("enderchest", new EnderchestCommand(inventoryService));
        commands.put("fix", new FixCommand(inventoryService));
        commands.put("tpa", new TpaCommand(teleportService));
        commands.put("tpaccept", new TpaCommand(teleportService));
        commands.put("tpdeny", new TpaCommand(teleportService));
        commands.put("trash", new TrashCommand(inventoryService));

        commands.forEach((name, executor) -> plugin.getCommand(name).setExecutor(executor));
    }
}