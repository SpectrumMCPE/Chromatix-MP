package chromatix.level.generator.object.legacytree;

import chromatix.block.BlockCrimsonStem;
import chromatix.block.BlockNetherWartBlock;
import chromatix.block.BlockState;

public class LegacyCrimsonTree extends LegacyNetherTree {
    @Override
    public BlockState getTrunkBlockState() {
        return BlockCrimsonStem.PROPERTIES.getDefaultState();
    }

    @Override
    public BlockState getLeafBlockState() {
        return BlockNetherWartBlock.PROPERTIES.getDefaultState();
    }
}
