package chromatix.level.generator.feature.tree;

import chromatix.block.Block;
import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectSwampOakTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class SwampTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:swamp_oak_tree_feature";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return new ObjectSwampOakTree(7, 8);
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.SWAMP, definition);
    }

    @Override
    public int getMin() {
        return 3;
    }

    @Override
    public int getMax() {
        return 5;
    }

    @Override
    public String name() {
        return NAME;
    }

    protected boolean checkBlock(Block bl) {
        return bl.canBeReplaced();
    }
}
