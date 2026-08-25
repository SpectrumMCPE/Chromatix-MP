package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectBigMushroom;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class MushroomIslandMushroomFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:mushroom_island_surface_huge_mushroom_feature";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return new ObjectBigMushroom();
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
    public boolean canSpawnHere(BiomeDefinitionData definition) {
        return Registries.BIOME.containsTag(BiomeTags.MOOSHROOM_ISLAND, definition);
    }

    @Override
    public String name() {
        return NAME;
    }
}
