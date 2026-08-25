package chromatix.item;

import chromatix.block.Block;
import chromatix.block.BlockID;

public class ItemRedFlower extends Item implements AliasItem {

    public ItemRedFlower() {
        super(RED_FLOWER);
        this.block = Block.get(BlockID.POPPY);
    }

    @Override
    public String getAliasIdentifier() {
        return BlockID.POPPY;
    }
}