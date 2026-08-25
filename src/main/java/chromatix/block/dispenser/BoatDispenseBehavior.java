package chromatix.block.dispenser;

import chromatix.block.Block;
import chromatix.block.BlockFlowingWater;
import chromatix.entity.Entity;
import chromatix.entity.item.EntityBoat;
import chromatix.item.Item;
import chromatix.level.Level;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;

public class BoatDispenseBehavior extends DefaultDispenseBehavior {

    public BoatDispenseBehavior() {
        super();
    }

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Vector3 pos = block.getSide(face).multiply(1.125);

        Block target = block.getSide(face);

        if (target instanceof BlockFlowingWater) {
            pos.y += 1;
        } else if (!target.isAir() || !(target.down() instanceof BlockFlowingWater)) {
            return super.dispense(block, face, item);
        }

        spawnBoatEntity(block.level, target.getLocation().add(face.getXOffset() * 0.75, face.getYOffset() * 0.75, face.getZOffset() * 0.75).setYaw(face.getHorizontalAngle()), item);

        return null;
    }

    protected void spawnBoatEntity(Level level, Vector3 pos, Item item) {
        EntityBoat boat = new EntityBoat(level.getChunk(pos.getChunkX(), pos.getChunkZ()),
                Entity.getDefaultNBT(pos)
                        .putInt("Variant", item.getDamage())
        );
        boat.spawnToAll();
    }

}
