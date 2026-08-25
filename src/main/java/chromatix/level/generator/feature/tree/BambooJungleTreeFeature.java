package chromatix.level.generator.feature.tree;

import chromatix.block.Block;
import chromatix.block.BlockBamboo;
import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectJungleBigTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class BambooJungleTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:bamboo_jungle_surface_trees_feature";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return new ObjectJungleBigTree(10, 20);
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.BAMBOO, definition);
    }

    @Override
    public int getMin() {
        return -1;
    }

    @Override
    public int getMax() {
        return 1;
    }

    @Override
    protected boolean checkBlock(Block bl) {
        return super.checkBlock(bl) && !(bl instanceof BlockBamboo);
    }

    @Override
    public String name() {
        return NAME;
    }
}
