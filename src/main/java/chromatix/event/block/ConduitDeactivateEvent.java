package chromatix.event.block;

import chromatix.block.Block;
import chromatix.event.HandlerList;


public class ConduitDeactivateEvent extends BlockEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public ConduitDeactivateEvent(Block block) {
        super(block);
    }

}
