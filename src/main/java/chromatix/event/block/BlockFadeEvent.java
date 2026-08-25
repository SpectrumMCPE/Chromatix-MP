package chromatix.event.block;

import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class BlockFadeEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block newState;

    public BlockFadeEvent(Block block, Block newState) {
        super(block);
        this.newState = newState;
    }

    public Block getNewState() {
        return newState;
    }
}
