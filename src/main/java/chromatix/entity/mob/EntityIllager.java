package chromatix.entity.mob;

import chromatix.entity.Entity;
import chromatix.entity.EntityWalkable;
import chromatix.entity.passive.EntityVillagerV2;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

/**
 * @author PikyCZ
 */
public abstract class EntityIllager extends EntityMob implements EntityWalkable {
    public EntityIllager(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return switch (entity.getIdentifier()) {
            case VILLAGER ->
                    entity instanceof EntityVillagerV2 villager && !villager.isBaby();
            case IRON_GOLEM, WANDERING_TRADER -> true;
            default -> super.attackTarget(entity);
        };
    }
}
