package gg.kite.core;

import gg.kite.core.commands.CommandFactory;
import gg.kite.core.config.ConfigManager;
import gg.kite.core.listeners.GodModeListener;
import gg.kite.core.listeners.TpaListener;
import gg.kite.core.listeners.TrashListener;
import gg.kite.core.modules.PluginModule;
import gg.kite.core.services.GodModeService;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public class Main extends JavaPlugin {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private PluginModule module;

    @Override
    public void onEnable() {
        try {
            logger.info("Initializing KiteUtility plugin...");

            ConfigManager configManager = new ConfigManager(this);
            configManager.loadConfig();

            module = new PluginModule(this, configManager);
            module.configure();

            CommandFactory commandFactory = module.getCommandFactory();
            commandFactory.registerCommands();

            GodModeService godModeService = module.getGodModeService();
            getServer().getPluginManager().registerEvents(new GodModeListener(godModeService), this);
            getServer().getPluginManager().registerEvents(new TrashListener(), this);
            getServer().getPluginManager().registerEvents(new TpaListener(module.getTeleportService()), this);

            logger.info("KiteUtility plugin enabled successfully.");
        } catch (Exception e) {
            logger.error("Failed to enable KiteUtility plugin", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        logger.info("Disabling KiteUtility plugin...");
        module.getTeleportService().clearRequests();
        logger.info("KiteUtility plugin disabled.");
    }
}