package chromatix.level.generator.feature.decoration;

import chromatix.block.BlockAir;
import chromatix.block.BlockAzalea;
import chromatix.block.BlockFloweringAzalea;
import chromatix.block.BlockMossBlock;
import chromatix.block.BlockMossCarpet;
import chromatix.block.BlockShortGrass;
import chromatix.block.BlockState;
import chromatix.block.BlockTallGrass;
import chromatix.block.property.CommonBlockProperties;
import chromatix.level.Level;
import chromatix.level.biome.BiomeID;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.level.generator.holder.NormalObjectHolder;
import chromatix.level.generator.noise.f.SimplexF;
import chromatix.level.generator.object.BlockManager;

import java.util.ArrayList;

import static chromatix.block.BlockID.DEEPSLATE;
import static chromatix.block.BlockID.STONE;

public class MossPatchSnapToFloorFeature extends GenerateFeature {

    public static final String NAME = "minecraft:moss_patch_snap_to_floor_feature";

    private static final BlockState MOSS = BlockMossBlock.PROPERTIES.getDefaultState();
    private static final BlockState SHORT_GRASS = BlockShortGrass.PROPERTIES.getDefaultState();
    private static final BlockState LOWER_TALL_GRASS = BlockTallGrass.PROPERTIES.getBlockState(CommonBlockProperties.UPPER_BLOCK_BIT.createValue(false));
    private static final BlockState UPPER_TALL_GRASS = BlockTallGrass.PROPERTIES.getBlockState(CommonBlockProperties.UPPER_BLOCK_BIT.createValue(true));
    private static final BlockState MOSS_CARPET = BlockMossCarpet.PROPERTIES.getDefaultState();
    private static final BlockState AZALEE = BlockAzalea.PROPERTIES.getDefaultState();
    private static final BlockState AZALEE_FLOWER = BlockFloweringAzalea.PROPERTIES.getDefaultState();

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ));
        SimplexF noise = ((NormalObjectHolder) level.getGeneratorObjectHolder()).getFeatureHolder().getMossPatchSnapToFloor();
        BlockManager manager = new BlockManager(level);
        for(int x = 0; x < 16; x++) {
            int baseX = ((chunk.getX() << 4) + x);
            for(int z = 0; z < 16; z++) {
                int baseZ = ((chunk.getZ() << 4) + z);
                if(noise.noise2D(baseX, baseZ, true) > 0) {
                    for(int y : getHighestWorkableBlocks(chunk, x, z)) {
                        manager.setBlockStateAt(baseX, y, baseZ, MOSS);
                        int rnd = random.nextInt(20);
                        switch (rnd){
                            case 0, 1, 2, 3, 4 -> chunk.setBlockState(x, y+1, z, SHORT_GRASS);
                            case 5, 6, 7, 8, 9 -> {
                                manager.setBlockStateAt(baseX, y+1, baseZ, LOWER_TALL_GRASS);
                                manager.setBlockStateAt(baseX, y+2, baseZ, UPPER_TALL_GRASS);
                            }
                            case 10, 11, 12, 13 -> manager.setBlockStateAt(baseX, y+1, baseZ, MOSS_CARPET);
                            case 14 -> manager.setBlockStateAt(baseX, y+1, baseZ, random.nextBoolean() ? AZALEE : AZALEE_FLOWER);
                        }
                    }
                }
            }
        }
        queueObject(chunk, manager);
    }


    private ArrayList<Integer> getHighestWorkableBlocks(IChunk chunk, int x, int z) {
        int y;
        ArrayList<Integer> blockYs = new ArrayList<>();
        for (y = chunk.getHeightMap(x, z); y > chunk.getLevel().getMinHeight(); --y) {
            if(chunk.getSection(y >> 4).getBiomeId(x, y & 0x0f, z) == BiomeID.LUSH_CAVES) {
                String b = chunk.getBlockState(x, y, z).getIdentifier();
                if ((b == STONE || b == DEEPSLATE) && chunk.getBlockState(x, y + 1, z) == BlockAir.STATE) {
                    blockYs.add(y);
                }
            }
        }
        return blockYs;
    }
    @Override
    public String name() {
        return NAME;
    }
}
