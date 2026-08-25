package chromatix.event.level;

import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.level.format.IChunk;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class ChunkUnloadEvent extends ChunkEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public ChunkUnloadEvent(IChunk chunk) {
        super(chunk);
    }

}
