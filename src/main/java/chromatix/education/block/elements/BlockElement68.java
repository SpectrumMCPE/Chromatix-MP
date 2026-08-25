package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement68 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_68");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement68() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement68(BlockState blockstate) {
        super(blockstate);
    }
}