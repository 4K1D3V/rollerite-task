package gg.kite.core.modules;

import gg.kite.core.commands.CommandFactory;
import gg.kite.core.config.ConfigManager;
import gg.kite.core.services.GodModeService;
import gg.kite.core.services.InventoryService;
import gg.kite.core.services.PlayerService;
import gg.kite.core.services.TeleportService;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule {
    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private PlayerService playerService;
    private InventoryService inventoryService;
    private GodModeService godModeService;
    private TeleportService teleportService;
    private CommandFactory commandFactory;

    public PluginModule(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void configure() {
        playerService = new PlayerService(configManager.getMessageConfig());
        inventoryService = new InventoryService(configManager.getMessageConfig());
        godModeService = new GodModeService(configManager.getMessageConfig());
        teleportService = new TeleportService(configManager.getMessageConfig(), configManager);
        commandFactory = new CommandFactory(plugin, playerService, inventoryService, godModeService, teleportService);
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public GodModeService getGodModeService() {
        return godModeService;
    }

    public TeleportService getTeleportService() {
        return teleportService;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }
}