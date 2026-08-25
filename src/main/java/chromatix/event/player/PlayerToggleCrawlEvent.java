package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class PlayerToggleCrawlEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final boolean isCrawling;

    public PlayerToggleCrawlEvent(Player player, boolean isSneaking) {
        this.player = player;
        this.isCrawling = isSneaking;
    }

    public boolean isCrawling() {
        return this.isCrawling;
    }

}
