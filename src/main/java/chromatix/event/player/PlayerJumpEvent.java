package chromatix.event.player;

import chromatix.Player;
import chromatix.event.HandlerList;

public class PlayerJumpEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerJumpEvent(Player player){
        this.player = player;
    }
}
