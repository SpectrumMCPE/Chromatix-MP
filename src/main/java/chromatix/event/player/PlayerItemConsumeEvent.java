package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;

/**
 * Called when a player eats something
 */
public class PlayerItemConsumeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item item;

    public PlayerItemConsumeEvent(Player player, Item item) {
        this.player = player;
        this.item = item;
    }

    public Item getItem() {
        return this.item.clone();
    }
}
