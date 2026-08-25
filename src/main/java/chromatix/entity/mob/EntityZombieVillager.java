package chromatix.entity.mob;

import chromatix.entity.EntitySmite;
import chromatix.entity.EntityWalkable;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @deprecated Zombie Villagers were replaced by {@link EntityZombieVillagerV2} in 1.14
 */
@Deprecated
public class EntityZombieVillager extends EntityZombie implements EntityWalkable, EntitySmite {

    @Override
    @NotNull
    public String getIdentifier() {
        return ZOMBIE_VILLAGER;
    }

    public EntityZombieVillager(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public String getOriginalName() {
        return "Zombie Villager";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("zombie", "zombie_villager", "undead", "monster", "mob");
    }

}
