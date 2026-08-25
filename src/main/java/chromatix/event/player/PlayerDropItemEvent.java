package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;

public class PlayerDropItemEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item drop;

    public PlayerDropItemEvent(Player player, Item drop) {
        this.player = player;
        this.drop = drop;
    }

    public Item getItem() {
        return this.drop;
    }
}
