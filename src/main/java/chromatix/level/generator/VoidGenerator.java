package chromatix.level.generator;

import chromatix.Server;
import chromatix.level.DimensionData;
import chromatix.level.generator.stages.FinishedStage;
import chromatix.level.generator.stages.LightPopulationStage;
import chromatix.level.generator.stages.VoidGenerateStage;
import chromatix.registry.Registries;

import java.util.Map;

public class VoidGenerator extends Generator {
    public VoidGenerator(DimensionData dimensionData, Map<String, Object> options) {
        super(dimensionData, options);
    }

    @Override
    public void stages(GenerateStage.Builder builder) {
        builder.start(Registries.GENERATE_STAGE.get(VoidGenerateStage.NAME));
        if (Server.getInstance().getSettings().chunkSettings().lightUpdates()) {
            builder.next(Registries.GENERATE_STAGE.get(LightPopulationStage.NAME));
        }
        builder.next(Registries.GENERATE_STAGE.get(FinishedStage.NAME));
    }

    @Override
    public String getName() {
        return "void";
    }
}
