package chromatix.item;

import chromatix.utils.DyeColor;

public class ItemLimeDye extends ItemDye {
    public ItemLimeDye() {
        super(LIME_DYE);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.LIME;
    }

    @Override
    public void setDamage(int meta) {
        
    }
}