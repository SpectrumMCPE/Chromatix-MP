package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement95 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_95");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement95() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement95(BlockState blockstate) {
        super(blockstate);
    }
}