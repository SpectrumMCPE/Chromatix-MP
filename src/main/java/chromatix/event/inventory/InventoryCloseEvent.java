package chromatix.event.inventory;

import chromatix.Player;
import chromatix.event.HandlerList;
import chromatix.inventory.Inventory;
/**
 * @author Box (Nukkit Project)
 */
public class InventoryCloseEvent extends InventoryEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player who;

    public InventoryCloseEvent(Inventory inventory, Player who) {
        super(inventory);
        this.who = who;
    }

    public Player getPlayer() {
        return this.who;
    }
}
