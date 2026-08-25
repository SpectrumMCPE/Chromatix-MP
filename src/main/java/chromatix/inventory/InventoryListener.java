package chromatix.inventory;


import chromatix.item.Item;


public interface InventoryListener {
    void onInventoryChanged(Inventory inventory, Item oldItem, int slot);
}
