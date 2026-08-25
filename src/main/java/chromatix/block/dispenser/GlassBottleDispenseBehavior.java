package chromatix.block.dispenser;

import chromatix.block.Block;
import chromatix.block.BlockFlowingWater;
import chromatix.item.Item;
import chromatix.math.BlockFace;


public class GlassBottleDispenseBehavior extends DefaultDispenseBehavior {

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Block target = block.getSide(face);
        if (target instanceof BlockFlowingWater w && w.getLiquidDepth() == 0)
            return Item.get(Item.POTION);
        return super.dispense(block, face, item);
    }
}
