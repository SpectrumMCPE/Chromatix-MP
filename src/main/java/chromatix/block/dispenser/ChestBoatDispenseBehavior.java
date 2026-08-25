package chromatix.block.dispenser;

import chromatix.entity.Entity;
import chromatix.entity.item.EntityChestBoat;
import chromatix.item.Item;
import chromatix.item.ItemChestBoat;
import chromatix.level.Level;
import chromatix.math.Vector3;


public class ChestBoatDispenseBehavior extends BoatDispenseBehavior{
    @Override
    protected void spawnBoatEntity(Level level, Vector3 pos, Item item) {
        EntityChestBoat boat = new EntityChestBoat(level.getChunk(pos.getChunkX(), pos.getChunkZ()),
                Entity.getDefaultNBT(pos)
                        .putInt("Variant", ((ItemChestBoat) item).getBoatId())
        );
        boat.spawnToAll();
    }
}
