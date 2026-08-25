package chromatix.event.player;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class PlayerBedEnterEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block bed;

    public PlayerBedEnterEvent(Player player, Block bed) {
        this.player = player;
        this.bed = bed;
    }

    public Block getBed() {
        return bed;
    }
}
