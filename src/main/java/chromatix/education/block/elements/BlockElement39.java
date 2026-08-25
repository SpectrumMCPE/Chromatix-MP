package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement39 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_39");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement39() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement39(BlockState blockstate) {
        super(blockstate);
    }
}