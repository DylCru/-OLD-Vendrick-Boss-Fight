package me.crazyrain.vendrickbossfight.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;
import java.util.UUID;

public class VendrickStartAttackEvent extends Event {
    private static final HandlerList handlers = new HandlerList();


    private int phase;

    public VendrickStartAttackEvent(int phase){
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
