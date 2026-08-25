package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardGlass extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties(HARD_GLASS);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockHardGlass() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockHardGlass(BlockState blockstate) {
        super(blockstate);
    }
}