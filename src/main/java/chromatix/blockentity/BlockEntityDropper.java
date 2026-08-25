package chromatix.blockentity;

import chromatix.block.BlockID;
import chromatix.inventory.DropperInventory;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;


public class BlockEntityDropper extends BlockEntityEjectable {


    public BlockEntityDropper(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected DropperInventory createInventory() {
        inventory = new DropperInventory(this);
        return getInventory();
    }

    @Override
    protected String getBlockEntityName() {
        return BlockEntity.DROPPER;
    }

    @Override
    public DropperInventory getInventory() {
        return (DropperInventory) inventory;
    }

    @Override
    public boolean isBlockEntityValid() {
        return this.getLevelBlock().getId() == BlockID.DROPPER;
    }
}
