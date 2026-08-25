package chromatix.item;

import chromatix.block.Block;
import chromatix.block.BlockID;

public class ItemWheatSeeds extends Item {
    public ItemWheatSeeds() {
        this(0, 1);
    }

    public ItemWheatSeeds(Integer meta) {
        this(meta, 1);
    }

    public ItemWheatSeeds(Integer meta, int count) {
        super(WHEAT_SEEDS, 0, count, "Wheat Seeds");
        this.block = Block.get(BlockID.WHEAT);
    }
}