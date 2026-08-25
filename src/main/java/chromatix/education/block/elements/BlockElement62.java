package chromatix.education.block.elements;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockElement62 extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties("minecraft:element_62");

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElement62() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElement62(BlockState blockstate) {
        super(blockstate);
    }
}