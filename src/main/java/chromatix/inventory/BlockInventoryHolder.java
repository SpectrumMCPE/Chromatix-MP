package chromatix.inventory;

import chromatix.block.Block;

import java.util.function.Supplier;

public interface BlockInventoryHolder extends InventoryHolder {
    String KEY = "inventory";

    Supplier<Inventory> blockInventorySupplier();

    default Block getBlock() {
        return (Block) this;
    }

    @Override
    default Inventory getInventory() {
        return blockInventorySupplier().get();
    }

}
