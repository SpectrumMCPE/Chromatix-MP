package chromatix.block.dispenser;

import chromatix.item.Item;
import chromatix.math.BlockFace;

/**
 * @author CreeperFace
 */
public interface DispenseBehavior {


    Item dispense(BlockDispenser block, BlockFace face, Item item);

}
