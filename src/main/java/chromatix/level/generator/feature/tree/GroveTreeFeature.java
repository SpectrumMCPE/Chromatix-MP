package chromatix.level.generator.feature.tree;

import chromatix.block.BlockSnowLayer;
import chromatix.block.BlockSpruceLeaves;
import chromatix.block.BlockState;
import chromatix.level.Level;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.feature.GriddedFeature;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectSmallSpruceTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class GroveTreeFeature extends GriddedFeature {

    protected final static BlockState SNOW_LAYER = BlockSnowLayer.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:grove_spruce_tree_feature";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return new ObjectSmallSpruceTree();
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.GROVE, definition);
    }

    @Override
    public int getSplit() {
        return 4;
    }

    @Override
    public void apply(ChunkGenerateContext context) {
        super.apply(context);
        IChunk chunk = context.getChunk();
        Level level = chunk.getLevel();
        BlockManager object = new BlockManager(level);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int y = chunk.getHeightMap(x, z);
                BlockState support = chunk.getBlockState(x, y, z);
                if (support.toBlock() instanceof BlockSpruceLeaves) {
                    object.setBlockStateAt(x + (chunk.getX() << 4), y + 1, z + (chunk.getZ() << 4), SNOW_LAYER);
                }
            }
        }
        queueObject(chunk, object);
    }
}
