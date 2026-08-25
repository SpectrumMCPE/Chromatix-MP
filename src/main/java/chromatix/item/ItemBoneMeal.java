package chromatix.item;

import chromatix.utils.DyeColor;

public class ItemBoneMeal extends ItemDye {
    public ItemBoneMeal() {
        super(BONE_MEAL);
    }

    @Override
    public boolean isFertilizer() {
        return true;
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.WHITE;
    }

    @Override
    public void setDamage(int meta) {
    }
}