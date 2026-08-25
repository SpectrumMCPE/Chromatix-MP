package chromatix.block;

import chromatix.item.Item;
import chromatix.item.ItemTool;

public abstract class BlockDoubleWoodenSlab extends BlockDoubleSlabBase {
    public BlockDoubleWoodenSlab(BlockState blockstate) {
        super(blockstate);
    }
    @Override
    public String getName() {
        return "Double " + getSlabName() + " Wood Slab";
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    protected boolean isCorrectTool(Item item) {
        return true;
    }
}