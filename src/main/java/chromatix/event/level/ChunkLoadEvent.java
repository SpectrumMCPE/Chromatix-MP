package chromatix.event.level;

import chromatix.event.HandlerList;
import chromatix.level.format.IChunk;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class ChunkLoadEvent extends ChunkEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final boolean newChunk;

    public ChunkLoadEvent(IChunk chunk, boolean newChunk) {
        super(chunk);
        this.newChunk = newChunk;
    }

    public boolean isNewChunk() {
        return newChunk;
    }
}
