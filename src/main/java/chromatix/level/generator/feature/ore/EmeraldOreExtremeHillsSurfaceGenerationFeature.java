package chromatix.level.generator.feature.ore;

import chromatix.block.BlockEmeraldOre;
import chromatix.block.BlockID;
import chromatix.block.BlockState;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.feature.CountGenerateFeature;
import chromatix.utils.random.RandomSourceProvider;

public class EmeraldOreExtremeHillsSurfaceGenerationFeature extends CountGenerateFeature {

    public static final String NAME = "minecraft:extreme_hills_after_surface_emerald_ore_feature";

    @Override
    public int getBase() {
        return -2000;
    }

    @Override
    public int getRandom() {
        return 2015;
    }

    @Override
    public void populate(ChunkGenerateContext context, RandomSourceProvider random) {
        IChunk chunk = context.getChunk();
        int x = random.nextInt(15);
        int z = random.nextInt(15);
        int y = chunk.getHeightMap(x, z);
        BlockState state = chunk.getBlockState(x, y, z);
        if(state.getIdentifier().equals(BlockID.STONE)) {
            chunk.setBlockState(x, y, z, BlockEmeraldOre.PROPERTIES.getDefaultState());
        }
    }

    @Override
    public String name() {
        return NAME;
    }
}
