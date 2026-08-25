package chromatix.education.block;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.BlockTorch;
import chromatix.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockColoredTorchRed extends BlockTorch {
    public static final BlockProperties PROPERTIES = new BlockProperties(COLORED_TORCH_RED,  CommonBlockProperties.TORCH_FACING_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockColoredTorchRed() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockColoredTorchRed(BlockState blockstate) {
        super(blockstate);
    }
}