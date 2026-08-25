package chromatix.blockentity;

import chromatix.inventory.InventoryHolder;

/**
 * Semantic interface
 */
public interface BlockEntityInventoryHolder extends BlockEntityNameable, InventoryHolder {
    default String getInventoryTitle() {
        return getName();
    }

    default void setInventoryTitle(String name) {
        setName(name);
    }
}
