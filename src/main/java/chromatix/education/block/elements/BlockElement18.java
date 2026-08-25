package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement18 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_18");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement18() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement18(BlockState blockstate) {
        super(blockstate);
    }
}