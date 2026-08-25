package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement66 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_66");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement66() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement66(BlockState blockstate) {
        super(blockstate);
    }
}