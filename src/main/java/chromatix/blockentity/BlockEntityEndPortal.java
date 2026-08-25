package chromatix.blockentity;

import chromatix.block.Block;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

/**
 * @author GoodLucky777
 */
public class BlockEntityEndPortal extends BlockEntitySpawnable {

    public BlockEntityEndPortal(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }
    
    @Override
    public boolean isBlockEntityValid() {
        return this.getLevel().getBlockIdAt(getFloorX(), getFloorY(), getFloorZ()) == Block.END_PORTAL;
    }
}
