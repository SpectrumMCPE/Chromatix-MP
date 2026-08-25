package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectCherryTree;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class CherryTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:cherry_grove_after_surface_cherry_tree_feature_rules";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return new ObjectCherryTree();
    }

    @Override
    public int getMin() {
        return 2;
    }

    @Override
    public int getMax() {
        return 4;
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.CHERRY_GROVE, definition);
    }

    @Override
    public String name() {
        return NAME;
    }
}
