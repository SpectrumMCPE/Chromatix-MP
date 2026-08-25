package chromatix.item;

import chromatix.block.Block;
import chromatix.block.BlockID;

public class ItemBeetrootSeeds extends Item {
    public ItemBeetrootSeeds() {
        super(BEETROOT_SEEDS);
        this.block = Block.get(BlockID.BEETROOT);
    }
}