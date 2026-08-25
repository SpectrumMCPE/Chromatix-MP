package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement64 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_64");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement64() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement64(BlockState blockstate) {
        super(blockstate);
    }
}