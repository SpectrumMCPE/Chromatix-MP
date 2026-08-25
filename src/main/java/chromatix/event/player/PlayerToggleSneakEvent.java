package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class PlayerToggleSneakEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final boolean isSneaking;

    public PlayerToggleSneakEvent(Player player, boolean isSneaking) {
        this.player = player;
        this.isSneaking = isSneaking;
    }

    public boolean isSneaking() {
        return this.isSneaking;
    }

}
