package chromatix.blockentity;

import chromatix.block.BlockID;
import chromatix.level.format.IChunk;
import chromatix.math.NukkitMath;
import chromatix.nbt.tag.CompoundTag;

/**
 * @author joserobjr
 */


public class BlockEntityTarget extends BlockEntity {


    public BlockEntityTarget(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean isBlockEntityValid() {
        return BlockID.TARGET.equals(getLevelBlock().getId());
    }

    public void setActivePower(int power) {
        this.nbt.putInt("activePower", power);
    }

    public int getActivePower() {
        return NukkitMath.clamp(getNbt().getInt("activePower"), 0, 15);
    }
}
