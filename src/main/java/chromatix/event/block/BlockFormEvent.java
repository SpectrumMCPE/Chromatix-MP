package chromatix.event.block;

import chromatix.block.Block;
import chromatix.event.HandlerList;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class BlockFormEvent extends BlockGrowEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public BlockFormEvent(Block block, Block newState) {
        super(block, newState);
    }

}
