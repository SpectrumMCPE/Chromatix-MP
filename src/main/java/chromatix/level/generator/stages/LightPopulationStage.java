package chromatix.level.generator.stages;

import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateStage;

public class LightPopulationStage extends GenerateStage {
    public static final String NAME = "light_population";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void apply(ChunkGenerateContext context) {
        final IChunk chunk = context.getChunk();
        if (chunk == null) {
            return;
        }
        chunk.recalculateHeightMap();
        chunk.populateSkyLight();
        chunk.setLightPopulated();
    }
}
