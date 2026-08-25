package chromatix.level.generator.stages;

import chromatix.level.format.ChunkState;
import chromatix.level.format.IChunk;
import chromatix.level.generator.ChunkGenerateContext;
import chromatix.level.generator.GenerateStage;
import chromatix.level.generator.object.BlockManager;

import java.util.concurrent.Executor;

public class GeneratedStage extends GenerateStage {
    public static final String NAME = "generated";

    @Override
    public void apply(ChunkGenerateContext context) {
        IChunk chunk = context.getChunk();
        chunk.setChunkState(ChunkState.GENERATED);
        chunk.setChanged(false);
        BlockManager.applyPendingSubChunkUpdates(context.getLevel(), chunk);
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
