package chromatix.block.dispenser;

import chromatix.block.Block;
import chromatix.block.BlockID;
import chromatix.item.Item;
import chromatix.item.ItemPotion;
import chromatix.math.BlockFace;
import chromatix.entity.effect.PotionType;


public class WaterBottleDispenseBehavior extends DefaultDispenseBehavior {
    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        if (((ItemPotion) item).getPotion() != PotionType.WATER)
            return super.dispense(block, face, item);
        var targetId = block.getSide(face).getId();
        if (targetId == BlockID.DIRT || targetId == BlockID.DIRT_WITH_ROOTS) {
            block.level.setBlock(block.getSideVec(face), Block.get(BlockID.MUD));
            return null;
        }
        return super.dispense(block, face, item);
    }
}
