package chromatix.item;

import chromatix.entity.EntityID;
import chromatix.registry.Registries;

public class ItemParchedSpawnEgg extends ItemSpawnEgg {
    public ItemParchedSpawnEgg() {
        super(PARCHED_SPAWN_EGG);
    }

    @Override
    public int getEntityNetworkId() {
        return Registries.ENTITY.getEntityNetworkId(EntityID.PARCHED);
    }

    @Override
    public void setDamage(int meta) {
    }
}