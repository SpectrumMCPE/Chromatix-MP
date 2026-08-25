package chromatix.level.generator.stages;

import chromatix.level.format.ChunkState;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateStage;

public class VoidGenerateStage extends GenerateStage {
    public static final String NAME = "void_generate";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        int minHeight = context.getGenerator().getDimensionData().getMinHeight();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setHeightMap(x, z, minHeight);
            }
        }
        chunk.setChunkState(ChunkState.POPULATED);
    }
}
