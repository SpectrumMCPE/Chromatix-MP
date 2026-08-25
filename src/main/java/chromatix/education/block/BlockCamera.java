package chromatix.education.block;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockCamera extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties(CAMERA);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockCamera() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockCamera(BlockState blockstate) {
        super(blockstate);
    }
}