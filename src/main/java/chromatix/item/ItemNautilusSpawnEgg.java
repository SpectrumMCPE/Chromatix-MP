package chromatix.item;

import chromatix.entity.EntityID;
import chromatix.registry.Registries;

public class ItemNautilusSpawnEgg extends ItemSpawnEgg {
    public ItemNautilusSpawnEgg() {
        super(NAUTILUS_SPAWN_EGG);
    }

    @Override
    public int getEntityNetworkId() {
        return Registries.ENTITY.getEntityNetworkId(EntityID.NAUTILUS);
    }

    @Override
    public void setDamage(int meta) {
    }
}