package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement84 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_84");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement84() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement84(BlockState blockstate) {
        super(blockstate);
    }
}