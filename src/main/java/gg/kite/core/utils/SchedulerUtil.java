package gg.kite.core.utils;

import gg.kite.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class SchedulerUtil {
    private static final JavaPlugin plugin = JavaPlugin.getPlugin(Main.class);

    public static void runTaskLater(Runnable task, long delay, TimeUnit unit) {
        long ticks = unit.toSeconds(delay) * 20;
        Bukkit.getScheduler().runTaskLater(plugin, task, ticks);
    }
}