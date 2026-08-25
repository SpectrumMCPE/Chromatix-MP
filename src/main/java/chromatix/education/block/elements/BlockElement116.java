package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement116 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_116");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement116() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement116(BlockState blockstate) {
        super(blockstate);
    }
}