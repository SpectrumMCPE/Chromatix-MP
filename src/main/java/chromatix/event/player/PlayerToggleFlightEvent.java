package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class PlayerToggleFlightEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final boolean isFlying;

    public PlayerToggleFlightEvent(Player player, boolean isFlying) {
        this.player = player;
        this.isFlying = isFlying;
    }

    public boolean isFlying() {
        return this.isFlying;
    }
}
