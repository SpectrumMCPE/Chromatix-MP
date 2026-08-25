package chromatix.level.generator.feature.decoration;

import chromatix.block.Block;
import chromatix.block.BlockShortGrass;
import chromatix.block.BlockState;
import chromatix.level.Position;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.object.BlockManager;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;

import static chromatix.level.generator.stages.normal.NormalTerrainStage.SEA_LEVEL;

public class TallGrassPatchFeature extends SurfaceGenerateFeature {

    private static final BlockState STATE = BlockShortGrass.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:scatter_tall_grass_feature";

    @Override
    public void populate(ChunkGenerateContext context, RandomSourceProvider random) {
        int biomeId = context.getChunk().getBiomeId(7, context.getLevel().getHeightMap((context.getChunk().getX() << 4) + 7, (context.getChunk().getZ() << 4) + 7), 7);
        if (Registries.BIOME.getTags(biomeId).contains(BiomeTags.MOOSHROOM_ISLAND)) {
            return;
        }

        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int x = random.nextBoundedInt(15);
        int z = random.nextBoundedInt(15);
        int y = context.getChunk().getHeightMap(x, z);
        int worldX = (chunkX << 4) + x;
        int worldZ = (chunkZ << 4) + z;
        Position position = new Position(worldX, y, worldZ, chunk.getLevel());
        while (!isSupportValid(chunk.getBlockState(x, y, z).toBlock(position)) && y > SEA_LEVEL) {
            y--;
            position.setY(y);
        }

        if (y < SEA_LEVEL || !isSupportValid(chunk.getBlockState(x, y, z).toBlock(position))) {
            return;
        }

        BlockManager manager = new BlockManager(chunk.getLevel());
        Block above = manager.getBlockIfCachedOrLoaded(worldX, y + 1, worldZ);
        if (!above.isAir()) {
            return;
        }

        BlockManager object = new BlockManager(chunk.getLevel());
        place(object, worldX, y + 1, worldZ);
        queueObject(chunk, object);
    }

    @Override
    public void place(BlockManager manager, int x, int y, int z) {
        manager.setBlockStateAt(x, y, z, STATE);
    }

    @Override
    public int getBase() {
        return 10;
    }

    @Override
    public int getRandom() {
        return 0;
    }

    @Override
    public String name() {
        return NAME;
    }
}
