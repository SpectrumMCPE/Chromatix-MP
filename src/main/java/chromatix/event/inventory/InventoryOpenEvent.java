package chromatix.event.inventory;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.inventory.Inventory;

/**
 * @author Box (Nukkit Project)
 */
public class InventoryOpenEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;

    public InventoryOpenEvent(Inventory inventory, Player player) {
        super(inventory);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
