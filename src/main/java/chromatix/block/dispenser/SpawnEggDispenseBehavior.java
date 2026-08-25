package chromatix.block.dispenser;

import chromatix.entity.Entity;
import chromatix.entity.EntityLiving;
import chromatix.item.Item;
import chromatix.item.ItemSpawnEgg;
import chromatix.level.vibration.VibrationEvent;
import chromatix.level.vibration.VibrationType;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;


public class SpawnEggDispenseBehavior extends DefaultDispenseBehavior {


    public SpawnEggDispenseBehavior() {
        super();
    }

    @Override
    public Item dispense(BlockDispenser block, BlockFace face, Item item) {
        Vector3 pos = block.getSide(face).add(0.5, 0.7, 0.5);

        Entity entity = Entity.createEntity(((ItemSpawnEgg)item).getEntityNetworkId(), block.level.getChunk(pos.getChunkX(), pos.getChunkZ()),
                Entity.getDefaultNBT(pos));

        this.success = entity != null;

        if (this.success) {
            if (item.hasCustomName() && entity instanceof EntityLiving) {
                entity.setNameTag(item.getCustomName());
            }

            entity.spawnToAll();

            block.level.getVibrationManager().callVibrationEvent(new VibrationEvent(this, pos.clone(), VibrationType.ENTITY_PLACE));
            return null;
        }

        return super.dispense(block, face, item);
    }
}
