package chromatix.blockentity;

import chromatix.block.BlockID;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

/**
 * @author Kevims KCodeYT
 */


public class BlockEntitySculkCatalyst extends BlockEntity {


    public BlockEntitySculkCatalyst(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean isBlockEntityValid() {
        return getLevelBlock().getId() == BlockID.SCULK_CATALYST;
    }

}
