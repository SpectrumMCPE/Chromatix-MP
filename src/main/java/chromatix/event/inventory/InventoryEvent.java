package chromatix.event.inventory;

import chromatix.Player;
import chromatix.event.Event;
import chromatix.inventory.Inventory;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class InventoryEvent extends Event {
    protected final Inventory inventory;

    public InventoryEvent(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player[] getViewers() {
        return this.inventory.getViewers().toArray(Player.EMPTY_ARRAY);
    }

}
