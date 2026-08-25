package chromatix.item;

import chromatix.block.Block;


public abstract class ItemHangingSign extends Item {
    public ItemHangingSign(String id) {
        super(id);
        this.block = Block.get(id);
    }

    @Override
    public int getMaxStackSize() {
        return 16;
    }
}
