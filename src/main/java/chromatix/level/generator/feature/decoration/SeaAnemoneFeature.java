package chromatix.level.generator.feature.decoration;

import chromatix.block.BlockState;
import chromatix.level.format.IChunk;
import chromatix.utils.random.RandomSourceProvider;

public class SeaAnemoneFeature extends AbstractCoralFeature {
    public static final String NAME = "minecraft:sea_anemone_feature";

    @Override
    public int getBase() {
        return 1;
    }

    @Override
    public int getRandom() {
        return 0;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    protected boolean placeFeature(IChunk chunk, RandomSourceProvider random, int x, int y, int z, BlockState coralState) {
        return placeCoralBlock(chunk, random, x, y, z, coralState);
    }
}
