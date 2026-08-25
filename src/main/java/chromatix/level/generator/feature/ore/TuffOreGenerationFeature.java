package chromatix.level.generator.feature.ore;

import chromatix.block.BlockState;
import chromatix.block.BlockTuff;

public class TuffOreGenerationFeature extends OreGeneratorFeature {

    private static final BlockState STATE = BlockTuff.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:overworld_underground_tuff_feature";

    @Override
    public BlockState getState(BlockState original) {
        return STATE;
    }

    @Override
    public int getClusterCount() {
        return 2;
    }

    @Override
    public int getClusterSize() {
        return 64;
    }

    @Override
    public int getMinHeight() {
        return -64;
    }

    @Override
    public int getMaxHeight() {
        return 0;
    }

    @Override
    public String name() {
        return NAME;
    }
}
