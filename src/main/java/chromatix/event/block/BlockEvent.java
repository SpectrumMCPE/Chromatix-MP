package chromatix.event.block;

import chromatix.block.Block;
import chromatix.event.Event;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class BlockEvent extends Event {

    protected final Block block;

    public BlockEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }
}
