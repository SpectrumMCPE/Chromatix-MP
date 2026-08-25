package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement93 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_93");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement93() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement93(BlockState blockstate) {
        super(blockstate);
    }
}