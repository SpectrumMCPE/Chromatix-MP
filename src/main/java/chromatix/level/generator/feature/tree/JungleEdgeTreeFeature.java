package chromatix.level.generator.feature.tree;

import chromatix.block.property.enums.WoodType;
import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectFallenTree;
import chromatix.level.generator.object.ObjectFancyOakTree;
import chromatix.level.generator.object.ObjectJungleTree;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class JungleEdgeTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:legacy:jungle_edge_tree_feature";


    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return switch (random.nextInt(5)) {
            case 0, 1 -> random.nextInt(100) == 0 ? new ObjectFallenTree(WoodType.JUNGLE) : new ObjectJungleTree(7, 8);
            default -> new ObjectFancyOakTree();
        };
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.EDGE, definition);
    }

    @Override
    public int getMin() {
        return 1;
    }

    @Override
    public int getMax() {
        return 2;
    }

    @Override
    public String name() {
        return NAME;
    }
}
