package chromatix.block;

import chromatix.item.Item;
import chromatix.item.ItemShulkerBox;
import chromatix.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BlockBlueShulkerBox extends BlockUndyedShulkerBox {
    public static final BlockProperties PROPERTIES = new BlockProperties(BLUE_SHULKER_BOX, Set.of(BlockTags.PNX_SHULKERBOX));

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockBlueShulkerBox() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockBlueShulkerBox(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public Item getShulkerBox() {
        return new ItemShulkerBox(11);
    }
}