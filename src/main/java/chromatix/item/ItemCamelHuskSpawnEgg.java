package chromatix.item;

import chromatix.entity.EntityID;
import chromatix.registry.Registries;

public class ItemCamelHuskSpawnEgg extends ItemSpawnEgg {
    public ItemCamelHuskSpawnEgg() {
        super(CAMEL_HUSK_SPAWN_EGG);
    }

    @Override
    public int getEntityNetworkId() {
        return Registries.ENTITY.getEntityNetworkId(EntityID.CAMEL_HUSK);
    }

    @Override
    public void setDamage(int meta) {
    }
}