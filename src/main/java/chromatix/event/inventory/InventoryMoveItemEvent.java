package chromatix.event.inventory;

import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.inventory.Inventory;
import chromatix.inventory.InventoryHolder;
import chromatix.item.Item;

/**
 * @author CreeperFace
 * <p>
 * Called when inventory transaction is not caused by a player
 */
public class InventoryMoveItemEvent extends InventoryEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Inventory targetInventory;
    private final InventoryHolder source;
    private final Action action;
    private Item item;

    public InventoryMoveItemEvent(Inventory from, Inventory targetInventory, InventoryHolder source, Item item, Action action) {
        super(from);
        this.targetInventory = targetInventory;
        this.source = source;
        this.item = item;
        this.action = action;
    }

    public Inventory getTargetInventory() {
        return targetInventory;
    }

    public InventoryHolder getSource() {
        return source;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Action getAction() {
        return action;
    }

    public enum Action {
        SLOT_CHANGE, //transaction between 2 inventories
        PICKUP,
        DROP,
        DISPENSE
    }
}
