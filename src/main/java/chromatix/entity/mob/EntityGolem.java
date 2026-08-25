package chromatix.entity.mob;

import chromatix.entity.Entity;
import chromatix.entity.EntityCanAttack;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.passive.EntityAllay;
import chromatix.event.entity.EntityDamageByEntityEvent;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

public abstract class EntityGolem extends EntityIntelligent implements EntityWalkable, EntityCanAttack {

    public EntityGolem(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (source instanceof EntityDamageByEntityEvent entityDamageByEntityEvent && !(entityDamageByEntityEvent.getDamager() instanceof EntityCreeper)) {
            getMemoryStorage().put(CoreMemoryTypes.ATTACK_TARGET, entityDamageByEntityEvent.getDamager());
        }
        return super.attack(source);
    }

    @Override
    public boolean attackTarget(Entity entity) {
        if (entity instanceof EntityGolem) return false;
        if (entity instanceof EntityAllay) return false;
        return entity instanceof EntityMob;
    }
}
