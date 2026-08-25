package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement20 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_20");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement20() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement20(BlockState blockstate) {
        super(blockstate);
    }
}