package chromatix.item.armor.copper;

import chromatix.item.Item;

public class ItemCopperHorseArmor extends Item {
    public ItemCopperHorseArmor() {
        super(COPPER_HORSE_ARMOR);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}