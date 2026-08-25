package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;

public class PlayerDuplicatedLoginEvent extends PlayerEvent implements Cancellable {
    public PlayerDuplicatedLoginEvent(Player player) {
        this.player = player;
    }
}
