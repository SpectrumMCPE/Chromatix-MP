package chromatix.level.generator.feature.decoration;

import chromatix.block.*;
import chromatix.block.property.CommonBlockProperties;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.feature.CountGenerateFeature;
import chromatix.level.generator.object.BlockManager;
import chromatix.math.NukkitMath;
import chromatix.utils.random.RandomSourceProvider;

public class SunflowerDouplePlantPatchFeature extends CountGenerateFeature implements Supportable {

    private final static BlockState SUNFLOWER_LOWER = BlockSunflower.PROPERTIES.getBlockState(CommonBlockProperties.UPPER_BLOCK_BIT.createValue(false));
    private final static BlockState SUNFLOWER_UPPER = BlockSunflower.PROPERTIES.getBlockState(CommonBlockProperties.UPPER_BLOCK_BIT.createValue(true));

    public static final String NAME = "minecraft:sunflower_double_plant_patch_feature";

    @Override
    public void populate(ChunkGenerateContext context, RandomSourceProvider random) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        int randomX = random.nextInt(15);
        int randomZ = random.nextInt(15);
        int sourceX = (chunkX << 4) + randomX;
        int sourceZ = (chunkZ << 4) + randomZ;

        BlockManager object = new BlockManager(chunk.getLevel());

        int radius = NukkitMath.randomRange(random, 2, 3);
        for (int x = sourceX - radius; x <= sourceX + radius; x++) {
            for (int z = sourceZ - radius; z <= sourceZ + radius; z++) {
                if ((x - sourceX) * (x - sourceX) + (z - sourceZ) * (z - sourceZ) <= radius * radius) {
                    if(random.nextFloat() < 0.3f) {
                        int height = level.getHeightMap(x, z);
                        BlockState topBlockState = level.getBlockStateAt(x, height, z);

                        if(isSupportValid(topBlockState.toBlock())) {
                            object.setBlockStateAt(x, height + 1, z, SUNFLOWER_LOWER);
                            object.setBlockStateAt(x, height + 2, z, SUNFLOWER_UPPER);
                        }
                    }
                }
            }
        }
        queueObject(chunk, object);

    }
    @Override
    public int getBase() {
        return -1;
    }

    @Override
    public int getRandom() {
        return 3;
    }

    public boolean isSupportValid(Block support) {
        return isSupportGrass(support);
    }

    @Override
    public String name() {
        return NAME;
    }
}
