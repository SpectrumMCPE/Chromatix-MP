package chromatix.level.generator.populator.nether;

import chromatix.block.BlockGravel;
import chromatix.block.BlockState;
import chromatix.level.biome.BiomeID;
import chromatix.level.generator.feature.ore.OreGeneratorFeature;
import chromatix.level.generator.object.BlockManager;
import chromatix.utils.random.RandomSourceProvider;

public class NetherGravelPopulator extends OreGeneratorFeature {

    public static final String NAME = "nether_gravel";

    protected static final BlockState STATE = BlockGravel.PROPERTIES.getDefaultState();
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
        return 33;
    }

    @Override
    public int getMinHeight() {
        return 5;
    }

    @Override
    public int getMaxHeight() {
        return 36;
    }

    @Override
    protected void spawn(BlockManager level, RandomSourceProvider rand, int x, int y, int z) {
        if(level.getLevel().getBiomeId(x, y, z) != BiomeID.BASALT_DELTAS)
            super.spawn(level, rand, x, y, z);
    }

    @Override
    public String name() {
        return NAME;
    }

}
