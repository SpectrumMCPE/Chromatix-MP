package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement106 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_106");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement106() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement106(BlockState blockstate) {
        super(blockstate);
    }
}