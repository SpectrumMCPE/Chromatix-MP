package chromatix.level.generator.feature.tree;

import chromatix.block.property.enums.WoodType;
import chromatix.level.generator.feature.GriddedFeature;
import chromatix.level.generator.object.ObjectFallenTree;
import chromatix.level.generator.object.ObjectFancyOakTree;
import chromatix.level.generator.object.ObjectJungleTree;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectJungleBigTree;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class JungleTreeFeature extends GriddedFeature {

    public static final String NAME = "minecraft:jungle_surface_trees_feature";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return switch (random.nextInt(10)) {
            case 0 -> new ObjectJungleBigTree(10, 20);
            case 4, 5, 6 -> random.nextInt(100) == 0 ? new ObjectFallenTree(WoodType.JUNGLE) : new ObjectJungleTree(4 + random.nextBoundedInt(7), 3);
            case 7, 8 -> new ObjectFancyOakTree();
            default -> random.nextInt(100) == 0 ? new ObjectFallenTree(WoodType.JUNGLE) : new ObjectJungleTree(7, 8);

        };
    }

    @Override
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.JUNGLE, definition);
    }


    @Override
    public String name() {
        return NAME;
    }
}
