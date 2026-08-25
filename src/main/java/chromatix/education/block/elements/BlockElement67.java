package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement67 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_67");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement67() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement67(BlockState blockstate) {
        super(blockstate);
    }
}