package chromatix.event.inventory;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.inventory.EnchantInventory;
import chromatix.item.Item;


public class EnchantItemEvent extends InventoryEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Player enchanter;
    private Item oldItem;
    private Item newItem;
    private int xpCost;

    public EnchantItemEvent(EnchantInventory inventory, Item oldItem, Item newItem, int cost, Player p) {
        super(inventory);
        this.oldItem = oldItem;
        this.newItem = newItem;
        this.xpCost = cost;
        this.enchanter = p;
    }

    public Item getOldItem() {
        return oldItem;
    }

    public void setOldItem(Item oldItem) {
        this.oldItem = oldItem;
    }

    public Item getNewItem() {
        return newItem;
    }

    public void setNewItem(Item newItem) {
        this.newItem = newItem;
    }

    public int getXpCost() {
        return xpCost;
    }

    public void setXpCost(int xpCost) {
        this.xpCost = xpCost;
    }

    public Player getEnchanter() {
        return enchanter;
    }

    public void setEnchanter(Player enchanter) {
        this.enchanter = enchanter;
    }
}
