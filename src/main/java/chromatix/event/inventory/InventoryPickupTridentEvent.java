package chromatix.event.inventory;

import chromatix.entity.projectile.EntityThrownTrident;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.inventory.Inventory;


public class InventoryPickupTridentEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityThrownTrident trident;

    public InventoryPickupTridentEvent(Inventory inventory, EntityThrownTrident trident) {
        super(inventory);
        this.trident = trident;
    }

    public EntityThrownTrident getTrident() {
        return trident;
    }
}
