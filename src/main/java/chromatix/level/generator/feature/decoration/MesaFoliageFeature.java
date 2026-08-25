package chromatix.level.generator.feature.decoration;

import chromatix.block.Block;
import chromatix.block.BlockAir;
import chromatix.block.BlockLeafLitter;
import chromatix.block.property.enums.MinecraftCardinalDirection;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateFeature;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

import java.util.List;

import static chromatix.block.property.CommonBlockProperties.GROWTH;
import static chromatix.block.property.CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION;

public class MesaFoliageFeature extends GenerateFeature {

    public static final String NAME = "minecraft:mesa_foliage_feature";

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
                if(random.nextInt(10) < 2) {
                    int y = chunk.getHeightMap(x, z) + 1;
                    List<String> tags = Registries.BIOME.getTags(chunk.getBiomeId(x, y, z));
                    if(tags.contains(BiomeTags.STONE)) {
                        Block support = chunk.getBlockState(x, y - 1, z).toBlock();
                        if (support.hasTag(BlockTags.DIRT)) {
                            if (chunk.getBlockState(x, y, z) == BlockAir.STATE) {
                                chunk.setBlockState(x, y, z, BlockLeafLitter.PROPERTIES.getBlockState(
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
