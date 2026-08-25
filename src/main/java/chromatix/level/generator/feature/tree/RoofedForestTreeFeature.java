package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectDarkOakTree;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class RoofedForestTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:roofed_forest_tree_feature_rules";

    private final static ObjectGenerator GENERATOR = new ObjectDarkOakTree();

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return GENERATOR;
    }

    @Override
    public int getMin() {
        return 8;
    }

    @Override
    public int getMax() {
        return 10;
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.ROOFED, definition);
    }

    @Override
    public String name() {
        return NAME;
    }
}
