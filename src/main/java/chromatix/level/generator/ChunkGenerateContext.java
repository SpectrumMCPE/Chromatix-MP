package chromatix.level.generator;

import chromatix.level.Level;
import chromatix.level.format.IChunk;
import lombok.Getter;

@Getter
public final class ChunkGenerateContext {
    private final Level level;
    private final Generator generator;
    private final IChunk chunk;

    public ChunkGenerateContext(Generator generator, Level level, IChunk chunk) {
        this.level = level;
        this.generator = generator;
        this.chunk = chunk;
    }
}
