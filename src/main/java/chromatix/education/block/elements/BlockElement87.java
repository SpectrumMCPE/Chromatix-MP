package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement87 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_87");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement87() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement87(BlockState blockstate) {
        super(blockstate);
    }
}