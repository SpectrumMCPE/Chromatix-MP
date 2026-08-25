package chromatix.level.generator;

import chromatix.level.DimensionData;
import chromatix.level.Level;
import chromatix.level.generator.biome.BiomePicker;
import chromatix.level.generator.biome.TheEndBiomePicker;
import chromatix.level.generator.biome.result.TheEndBiomeResult;
import chromatix.level.generator.holder.TheEndObjectHolder;
import chromatix.level.generator.holder.ObjectHolder;
import chromatix.level.generator.stages.BiomeMapStage;
import chromatix.level.generator.stages.GeneratedStage;
import chromatix.level.generator.stages.LightPopulationStage;
import chromatix.level.generator.stages.end.TheEndPopulatorStage;
import chromatix.level.generator.stages.end.TheEndTerrainStage;
import chromatix.level.generator.stages.FinishedStage;
import chromatix.registry.Registries;
import chromatix.utils.random.Xoroshiro128;

import java.util.Map;

public class TheEnd extends PopulatedGenerator implements BiomedGenerator {

    public TheEnd(DimensionData dimensionData, Map<String, Object> options) {
        super(dimensionData, options);
    }

    @Override
    public void stages(GenerateStage.Builder builder) {
        builder.start(Registries.GENERATE_STAGE.get(BiomeMapStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(TheEndTerrainStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(GeneratedStage.NAME));

        builder.next(Registries.GENERATE_STAGE.get(TheEndPopulatorStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(LightPopulationStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(FinishedStage.NAME));
    }

    @Override
    public BiomePicker<TheEndBiomeResult> createBiomePicker(Level level) {
        return new TheEndBiomePicker();
    }

    @Override
    public ObjectHolder createObjectHolder(Level level) {
        return new TheEndObjectHolder(new Xoroshiro128(level.getSeed()));
    }

    @Override
    public String getName() {
        return "the_end";
    }
}
