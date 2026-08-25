package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class PlayerToggleSprintEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final boolean isSprinting;

    public PlayerToggleSprintEvent(Player player, boolean isSprinting) {
        this.player = player;
        this.isSprinting = isSprinting;
    }

    public boolean isSprinting() {
        return this.isSprinting;
    }

}
