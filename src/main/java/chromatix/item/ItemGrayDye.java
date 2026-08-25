package chromatix.item;

import chromatix.utils.DyeColor;

public class ItemGrayDye extends ItemDye {
    public ItemGrayDye() {
        super(GRAY_DYE);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.GRAY;
    }

    @Override
    public void setDamage(int meta) {

    }
}