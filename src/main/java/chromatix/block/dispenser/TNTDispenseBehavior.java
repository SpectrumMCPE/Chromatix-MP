package chromatix.block.dispenser;

import chromatix.entity.Entity;
import chromatix.entity.item.EntityTnt;
import chromatix.item.Item;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;


public class TNTDispenseBehavior extends DefaultDispenseBehavior {


    public TNTDispenseBehavior() {
        super();
    }

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Vector3 pos = block.getSide(face).add(0.5, 0, 0.5);

        EntityTnt tnt = new EntityTnt(block.level.getChunk(pos.getChunkX(), pos.getChunkZ()),
                Entity.getDefaultNBT(pos));
        tnt.spawnToAll();

        return null;
    }

}
