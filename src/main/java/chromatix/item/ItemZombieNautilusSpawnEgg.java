package chromatix.item;

import chromatix.entity.EntityID;
import chromatix.registry.Registries;

public class ItemZombieNautilusSpawnEgg extends ItemSpawnEgg {
    public ItemZombieNautilusSpawnEgg() {
        super(ZOMBIE_NAUTILUS_SPAWN_EGG);
    }

    @Override
    public int getEntityNetworkId() {
        return Registries.ENTITY.getEntityNetworkId(EntityID.ZOMBIE_NAUTILUS);
    }

    @Override
    public void setDamage(int meta) {
    }
}