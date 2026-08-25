package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectJungleBush;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;

public class JungleBushFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:jungle_bush";

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return new ObjectJungleBush();
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
