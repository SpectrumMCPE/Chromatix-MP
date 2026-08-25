package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement6 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_6");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement6() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement6(BlockState blockstate) {
        super(blockstate);
    }
}