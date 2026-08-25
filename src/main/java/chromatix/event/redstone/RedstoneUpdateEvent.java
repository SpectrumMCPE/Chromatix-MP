package chromatix.event.redstone;

import chromatix.block.Block;
import chromatix.event.HandlerList;
import chromatix.event.block.BlockUpdateEvent;

/**
 * @author Angelic47 (Nukkit Project)
 */
public class RedstoneUpdateEvent extends BlockUpdateEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public RedstoneUpdateEvent(Block source) {
        super(source);
    }

}

