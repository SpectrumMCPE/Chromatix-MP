package chromatix.level.generator;

import chromatix.level.DimensionData;
import chromatix.level.Level;
import chromatix.level.generator.biome.BiomePicker;
import chromatix.level.generator.biome.NetherBiomePicker;
import chromatix.level.generator.biome.result.NetherBiomeResult;
import chromatix.level.generator.holder.ObjectHolder;
import chromatix.level.generator.holder.NetherObjectHolder;
import chromatix.level.generator.stages.BiomeMapStage;
import chromatix.level.generator.stages.GeneratedStage;
import chromatix.level.generator.stages.LightPopulationStage;
import chromatix.level.generator.stages.FinishedStage;
import chromatix.level.generator.stages.nether.NetherPopulatorStage;
import chromatix.level.generator.stages.nether.NetherTerrainStage;
import chromatix.registry.Registries;
import chromatix.utils.random.NukkitRandom;

import java.util.Map;

public class Nether extends PopulatedGenerator implements BiomedGenerator {

    public Nether(DimensionData dimensionData, Map<String, Object> options) {
        super(dimensionData, options);
    }

    @Override
    public void stages(GenerateStage.Builder builder) {
        builder.start(Registries.GENERATE_STAGE.get(BiomeMapStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(NetherTerrainStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(GeneratedStage.NAME));

        builder.next(Registries.GENERATE_STAGE.get(NetherPopulatorStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(LightPopulationStage.NAME));
        builder.next(Registries.GENERATE_STAGE.get(FinishedStage.NAME));
    }

    @Override
    public BiomePicker<NetherBiomeResult> createBiomePicker(Level level) {
        return new NetherBiomePicker(new NukkitRandom(level.getSeed()));
    }

    @Override
    public ObjectHolder createObjectHolder(Level level) {
        return new NetherObjectHolder(new NukkitRandom(level.getSeed()));
    }

    @Override
    public String getName() {
        return "nether";
    }
}
