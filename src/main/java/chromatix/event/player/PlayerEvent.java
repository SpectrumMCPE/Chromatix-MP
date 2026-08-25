package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Event;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class PlayerEvent extends Event {
    protected Player player;

    public Player getPlayer() {
        return player;
    }
}
