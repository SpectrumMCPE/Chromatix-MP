package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement91 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_91");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement91() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement91(BlockState blockstate) {
        super(blockstate);
    }
}