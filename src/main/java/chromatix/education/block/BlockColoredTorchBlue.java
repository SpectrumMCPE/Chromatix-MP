package chromatix.education.block;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.BlockTorch;
import chromatix.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

public class BlockColoredTorchBlue extends BlockTorch {
    public static final BlockProperties PROPERTIES = new BlockProperties(COLORED_TORCH_BLUE,  CommonBlockProperties.TORCH_FACING_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockColoredTorchBlue() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockColoredTorchBlue(BlockState blockstate) {
        super(blockstate);
    }
}