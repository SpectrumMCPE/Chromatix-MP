package chromatix.item;

import chromatix.block.Block;
import chromatix.block.BlockID;

public class ItemRepeater extends Item {
    public ItemRepeater() {
        super(REPEATER, 0, 1, "Redstone Repeater");
        this.block = Block.get(BlockID.UNPOWERED_REPEATER);
    }
}