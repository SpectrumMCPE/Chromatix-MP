package chromatix.item;

import chromatix.block.Block;
import chromatix.block.BlockID;

public class ItemComparator extends Item {
    public ItemComparator() {
        this(0, 1);
    }

    public ItemComparator(Integer meta, int count) {
        super(COMPARATOR, meta, count, "Redstone Comparator");
        this.block = Block.get(BlockID.UNPOWERED_COMPARATOR);
    }
}