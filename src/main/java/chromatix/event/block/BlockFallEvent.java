package chromatix.event.block;

import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


public class BlockFallEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public BlockFallEvent(Block block) {
        super(block);
    }
}
