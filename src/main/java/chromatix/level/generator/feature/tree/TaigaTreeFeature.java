package chromatix.level.generator.feature.tree;

import chromatix.block.Block;
import chromatix.block.BlockFlowable;
import chromatix.block.BlockSnowLayer;
import chromatix.block.BlockState;
import chromatix.block.property.enums.WoodType;
import chromatix.level.Level;
import chromatix.level.biome.BiomeID;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.feature.GriddedFeature;
import chromatix.level.generator.object.BlockManager;
import chromatix.level.generator.object.ObjectFallenTree;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectSmallSpruceTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class TaigaTreeFeature extends GriddedFeature {

    protected final static BlockState SNOW_LAYER = BlockSnowLayer.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:taiga_surface_trees_feature";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return random.nextInt(100) == 0 ? new ObjectFallenTree(WoodType.SPRUCE) : new ObjectSmallSpruceTree();
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.TAIGA, definition);
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
                if(support.toBlock().isFullBlock()) {
                    int cx = x + (chunk.getX() << 4);
                    int cz = z + (chunk.getZ() << 4);
                    if(chunk.getBiomeId(x, y, z) == BiomeID.COLD_TAIGA) {
                        // root holds trunks placed by super.apply this pass - checking object would read
                        // the uncommitted level and bury the tree's lowest log under snow
                        Block above = root.getCachedBlock(cx, y + 1, cz);
                        if(above.isAir()) {
                            object.setBlockStateAt(cx, y + 1, cz, SNOW_LAYER);
                        } else if(above instanceof BlockFlowable) {
                            object.setBlockStateAt(cx, y + 1, cz, 1, SNOW_LAYER);
                        }
                    }
                }
            }
        }
        queueObject(chunk, object);
    }
}
