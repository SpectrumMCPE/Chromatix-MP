package chromatix.level.generator.populator.nether;

import chromatix.block.BlockAncientDebris;
import chromatix.block.BlockState;
import chromatix.level.generator.feature.ore.OreGeneratorFeature;

public class AncientDebrisSmallPopulator extends OreGeneratorFeature {

    public static final String NAME = "nether_ancientdebris_small";

    protected static final BlockState STATE = BlockAncientDebris.PROPERTIES.getDefaultState();
    @Override
    public BlockState getState(BlockState original) {
        return STATE;
    }

    @Override
    public int getClusterCount() {
        return 3;
    }

    @Override
    public int getClusterSize() {
        return 2;
    }

    @Override
    public int getMinHeight() {
        return 8;
    }

    @Override
    public int getMaxHeight() {
        return 119;
    }

    @Override
    public float getSkipAir() {
        return 1;
    }

    @Override
    public String name() {
        return NAME;
    }
}
