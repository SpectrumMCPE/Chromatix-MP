package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class PlayerItemHeldEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item item;
    private final int hotbarSlot;

    public PlayerItemHeldEvent(Player player, Item item, int hotbarSlot) {
        this.player = player;
        this.item = item;
        this.hotbarSlot = hotbarSlot;
    }

    public int getSlot() {
        return this.hotbarSlot;
    }

    public Item getItem() {
        return item;
    }

}
