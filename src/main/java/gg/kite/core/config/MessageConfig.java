package gg.kite.core.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class MessageConfig {
    private final FileConfiguration config;

    public MessageConfig(FileConfiguration config) {
        this.config = config;
    }

    public String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&',
                config.getString("messages." + key, "&cMessage not found."));
    }

    public String getMessage(String key, Map<String, String> placeholders) {
        String message = getMessage(key);
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }
}