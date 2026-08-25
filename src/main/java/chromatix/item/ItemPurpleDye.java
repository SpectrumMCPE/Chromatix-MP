package chromatix.item;

import chromatix.utils.DyeColor;

public class ItemPurpleDye extends ItemDye {
    public ItemPurpleDye() {
        super(PURPLE_DYE);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.PURPLE;
    }

    @Override
    public void setDamage(int meta) {

    }
}