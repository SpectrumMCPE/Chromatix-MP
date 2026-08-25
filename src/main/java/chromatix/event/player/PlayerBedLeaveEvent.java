package chromatix.event.player;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.HandlerList;

public class PlayerBedLeaveEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block bed;

    public PlayerBedLeaveEvent(Player player, Block bed) {
        this.player = player;
        this.bed = bed;
    }

    public Block getBed() {
        return bed;
    }
}
