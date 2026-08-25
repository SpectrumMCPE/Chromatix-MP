package chromatix.level.generator.feature.decoration;

import chromatix.block.Block;
import chromatix.block.BlockAir;
import chromatix.block.BlockPinkPetals;
import chromatix.block.property.enums.MinecraftCardinalDirection;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

import static chromatix.block.property.CommonBlockProperties.GROWTH;
import static chromatix.block.property.CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION;

public class PinkPetalsFeature extends GenerateFeature {

    public static final String NAME = "minecraft:cherry_grove_surface_pink_petals_feature_rules";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        Level level = chunk.getLevel();
        this.random.setSeed(level.getSeed() ^ Level.chunkHash(chunkX, chunkZ));
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (random.nextBoolean()) {
                    int y = chunk.getHeightMap(x, z) + 1;
                    if (Registries.BIOME.containsTag(BiomeTags.CHERRY_GROVE, chunk.getBiomeId(x, y, z))) {
                        Block support = chunk.getBlockState(x, y - 1, z).toBlock();
                        if (support.hasTag(BlockTags.GRASS)) {
                            if (chunk.getBlockState(x, y, z) == BlockAir.STATE) {
                                chunk.setBlockState(x, y, z, BlockPinkPetals.PROPERTIES.getBlockState(
                                        MINECRAFT_CARDINAL_DIRECTION.createValue(
                                                MinecraftCardinalDirection.values()[random.nextInt(MinecraftCardinalDirection.values().length - 1)]
                                        ),
                                        GROWTH.createValue(random.nextInt(3))
                                ));
                            }
                        }
                    }
                }
            }
        }
    }
}
