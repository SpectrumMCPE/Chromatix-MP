package chromatix.blockentity;

import chromatix.block.BlockID;
import chromatix.inventory.DispenserInventory;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;


public class BlockEntityDispenser extends BlockEntityEjectable {


    public BlockEntityDispenser(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected DispenserInventory createInventory() {
        inventory = new DispenserInventory(this);
        return getInventory();
    }

    @Override
    protected String getBlockEntityName() {
        return BlockEntity.DISPENSER;
    }

    @Override
    public DispenserInventory getInventory() {
        return (DispenserInventory) inventory;
    }

    @Override
    public boolean isBlockEntityValid() {
        return this.getLevelBlock().getId() == BlockID.DISPENSER;
    }
}
