package chromatix.item;

import chromatix.block.Block;
import chromatix.block.BlockID;
import chromatix.utils.DyeColor;

public class ItemCocoaBeans extends ItemDye {
    public ItemCocoaBeans() {
        super(COCOA_BEANS);
        this.block = Block.get(BlockID.COCOA);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.BROWN;
    }

    @Override
    public void setDamage(int meta) {
    }
}