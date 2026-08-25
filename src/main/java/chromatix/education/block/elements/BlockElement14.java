package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement14 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_14");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement14() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement14(BlockState blockstate) {
        super(blockstate);
    }
}