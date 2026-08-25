package chromatix.item;

import chromatix.utils.DyeColor;

public class ItemPinkDye extends ItemDye {
    public ItemPinkDye() {
        super(PINK_DYE);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.PINK;
    }

    @Override
    public void setDamage(int meta) {

    }
}