package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectMangroveTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class MangroveTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:mangrove_swamp_mangrove_tree_feature";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        ObjectMangroveTree tree = new ObjectMangroveTree(random.nextFloat() > 0.15F);
        tree.setWithBeenest(random.nextFloat() < 0.04F);
        return tree;
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.MANGROVE_SWAMP, definition);
    }

    @Override
    public int getMin() {
        return 12;
    }

    @Override
    public int getMax() {
        return 15;
    }

    @Override
    public String name() {
        return NAME;
    }
}
