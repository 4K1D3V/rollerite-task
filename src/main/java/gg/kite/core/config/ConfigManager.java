package gg.kite.core.config;

import gg.kite.core.Main;
import gg.kite.core.utils.LoggerFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.slf4j.Logger;

public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private final Main plugin;
    private FileConfiguration config;
    private MessageConfig messageConfig;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        try {
            plugin.saveDefaultConfig();
            config = plugin.getConfig();
            messageConfig = new MessageConfig(config);
            logger.info("Configuration loaded successfully.");
        } catch (Exception e) {
            logger.error("Failed to load configuration", e);
            throw new RuntimeException("Configuration loading failed", e);
        }
    }

    public void reloadConfig() {
        try {
            plugin.reloadConfig();
            config = plugin.getConfig();
            messageConfig = new MessageConfig(config);
            logger.info("Configuration reloaded successfully.");
        } catch (Exception e) {
            logger.error("Failed to reload configuration", e);
            throw new RuntimeException("Configuration reload failed", e);
        }
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public int getTpaTimeout() {
        return config.getInt("tpa.timeout", 30);
    }
}