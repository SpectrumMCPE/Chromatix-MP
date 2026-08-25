package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;

/**
 * @author CreeperFace
 * @since 18.3.2017
 */
public class PlayerMapInfoRequestEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item item;

    public PlayerMapInfoRequestEvent(Player p, Item item) {
        this.player = p;
        this.item = item;
    }

    public Item getMap() {
        return item;
    }

}
