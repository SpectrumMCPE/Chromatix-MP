package chromatix.event.inventory;

import chromatix.entity.projectile.EntityArrow;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.inventory.Inventory;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class InventoryPickupArrowEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityArrow arrow;

    public InventoryPickupArrowEvent(Inventory inventory, EntityArrow arrow) {
        super(inventory);
        this.arrow = arrow;
    }

    public EntityArrow getArrow() {
        return arrow;
    }
}
