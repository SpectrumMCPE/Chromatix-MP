package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectLegacyObjectWrapper;
import chromatix.level.generator.object.ObjectSavannaTree;
import chromatix.level.generator.object.legacytree.LegacyOakTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class SavannaTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:savanna_surface_trees_feature";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return random.nextInt(3) == 0 ? new ObjectLegacyObjectWrapper(new LegacyOakTree()) : new ObjectSavannaTree();
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
        return Registries.BIOME.containsTag(BiomeTags.SAVANNA, definition);
    }

    @Override
    public String name() {
        return NAME;
    }
}
