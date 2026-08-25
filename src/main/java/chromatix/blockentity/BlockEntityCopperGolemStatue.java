package chromatix.blockentity;

import chromatix.block.copper.golem.AbstractBlockCopperGolemStatue;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

/**
 * @author Buddelbubi
 * @see <a href="https://github.com/GeyserMC/Geyser/blob/master/core/src/main/java/org/geysermc/geyser/translator/level/block/entity/CopperBlockEntityTranslator.java#L35">NBT Info</a>
 */
public class BlockEntityCopperGolemStatue extends BlockEntitySpawnable {

    public BlockEntityCopperGolemStatue(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public void loadNBT() {
        super.loadNBT();
        if (!this.nbt.contains("Pose")) {
            this.setPose(CopperPose.STANDING);
        }
    }

    @Override
    public boolean isBlockEntityValid() {
        return this.getBlock() instanceof AbstractBlockCopperGolemStatue;
    }

    @Override
    public CompoundTag getSpawnCompound() {
        return super.getSpawnCompound()
                .putBoolean("isMovable", false)
                .putInt("Pose", this.getNbt().getInt("Pose"));
    }

    public void setPose(CopperPose pose) {
        this.nbt.putInt("Pose", pose.ordinal());
    }

    public CopperPose getPose() {
        return CopperPose.values()[this.getNbt().getInt("Pose")];
    }

    public enum CopperPose {
        STANDING,
        SITTING,
        RUNNING,
        STAR
    }

}
