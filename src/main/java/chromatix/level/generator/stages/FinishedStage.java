package chromatix.level.generator.stages;

import chromatix.level.format.ChunkState;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateStage;

import java.util.concurrent.Executor;

public class FinishedStage extends GenerateStage {
    public static final String NAME = "finished";

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        chunk.setChunkState(ChunkState.FINISHED);
        if(!chunk.getLevel().getServer().getSettings().chunkSettings().saveGenerated()) {
            chunk.setChanged(false);
        }
    }

    @Override
    public Executor getExecutor() {
        return Runnable::run;
    }

    @Override
    public String name() {
        return NAME;
    }
}
