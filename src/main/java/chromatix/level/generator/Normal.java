package chromatix.level.generator;

import chromatix.level.DimensionData;
import chromatix.level.Level;
import chromatix.level.generator.biome.BiomePicker;
import chromatix.level.generator.biome.OverworldBiomePicker;
import chromatix.level.generator.biome.result.OverworldBiomeResult;
import chromatix.level.generator.holder.ObjectHolder;
import chromatix.level.generator.holder.NormalObjectHolder;
import chromatix.level.generator.stages.GeneratedStage;
import chromatix.level.generator.stages.LightPopulationStage;
import chromatix.level.generator.stages.NormalChunkFeatureStage;
import chromatix.level.generator.stages.FinishedStage;
import chromatix.level.generator.stages.BiomeMapStage;
import chromatix.level.generator.stages.normal.NormalPopulatorStage;
import chromatix.level.generator.stages.normal.NormalSurfaceDataStage;
import chromatix.level.generator.stages.normal.NormalSurfaceOverwriteStage;
import chromatix.level.generator.stages.normal.NormalTerrainStage;
import chromatix.registry.Registries;
import chromatix.utils.random.Xoroshiro128;

import java.util.Map;

/**
 * @author Buddelbubi
 */
public class Normal extends PopulatedGenerator implements BiomedGenerator {

    public Normal(DimensionData dimensionData, Map<String, Object> options) {
        super(dimensionData, options);
    }

    @Override
    public void stages(GenerateStage.Builder builder) {
        builder.start(Registries.GENERATE_STAGE.get(NormalTerrainStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(BiomeMapStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(NormalSurfaceDataStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(NormalSurfaceOverwriteStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(GeneratedStage.NAME));

        builder.next(Registries.GENERATE_STAGE.get(NormalPopulatorStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(NormalChunkFeatureStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(LightPopulationStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(FinishedStage.NAME));
    }

    @Override
    public BiomePicker<OverworldBiomeResult> createBiomePicker(Level level) {
        return new OverworldBiomePicker(level);
    }

    @Override
    public ObjectHolder createObjectHolder(Level level) {
        return new NormalObjectHolder(new Xoroshiro128(level.getSeed()));
    }

    @Override
    public String getName() {
        return "normal";
    }

}
