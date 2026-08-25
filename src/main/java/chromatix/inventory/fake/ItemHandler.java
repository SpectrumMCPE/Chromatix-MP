package chromatix.inventory.fake;

import chromatix.event.inventory.ItemStackRequestActionEvent;
import chromatix.item.Item;

@FunctionalInterface
public interface ItemHandler {
    void handle(FakeInventory fakeInventory, int slot, Item oldItem, Item newItem, ItemStackRequestActionEvent event);
}
