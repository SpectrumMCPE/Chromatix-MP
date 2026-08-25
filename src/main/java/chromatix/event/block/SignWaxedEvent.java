package chromatix.event.block;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


public class SignWaxedEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;
    private final boolean waxed;

    public SignWaxedEvent(Block block, Player player, boolean waxed) {
        super(block);
        this.player = player;
        this.waxed = waxed;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isWaxed() {
        return waxed;
    }
}