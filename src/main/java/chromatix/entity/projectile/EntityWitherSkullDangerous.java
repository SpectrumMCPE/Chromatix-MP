package chromatix.entity.projectile;

import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;


public class EntityWitherSkullDangerous extends EntityWitherSkull {

    @Override
    @NotNull public String getIdentifier() {
        return WITHER_SKULL_DANGEROUS;
    }

    public EntityWitherSkullDangerous(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected float getStrength() {
        return 1.5f;
    }
}
