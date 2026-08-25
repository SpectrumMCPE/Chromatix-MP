package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement46 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_46");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement46() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement46(BlockState blockstate) {
        super(blockstate);
    }
}