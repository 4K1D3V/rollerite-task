package gg.kite.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TpaRequestEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player sender;
    private final Player target;
    private boolean cancelled;

    public TpaRequestEvent(Player sender, Player target) {
        this.sender = sender;
        this.target = target;
        this.cancelled = false;
    }

    public Player getSender() {
        return sender;
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}