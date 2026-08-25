package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement60 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_60");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement60() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement60(BlockState blockstate) {
        super(blockstate);
    }
}