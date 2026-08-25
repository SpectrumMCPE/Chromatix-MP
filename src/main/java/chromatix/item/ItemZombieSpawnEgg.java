package chromatix.item;

import chromatix.entity.EntityID;
import chromatix.registry.Registries;

public class ItemZombieSpawnEgg extends ItemSpawnEgg {
    public ItemZombieSpawnEgg() {
        super(ZOMBIE_SPAWN_EGG);
    }

    @Override
    public int getEntityNetworkId() {
        return Registries.ENTITY.getEntityNetworkId(EntityID.ZOMBIE);
    }

    @Override
    public void setDamage(int meta) {
    }
}