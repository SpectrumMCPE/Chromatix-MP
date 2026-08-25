package chromatix.event.level;

import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.level.Level;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class LevelUnloadEvent extends LevelEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public LevelUnloadEvent(Level level) {
        super(level);
    }

}
