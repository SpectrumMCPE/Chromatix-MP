package chromatix.event.inventory;

import chromatix.entity.item.EntityItem;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.inventory.Inventory;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class InventoryPickupItemEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityItem item;

    public InventoryPickupItemEvent(Inventory inventory, EntityItem item) {
        super(inventory);
        this.item = item;
    }

    public EntityItem getItem() {
        return item;
    }
}
