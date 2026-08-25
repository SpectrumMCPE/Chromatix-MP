package chromatix.item;

import chromatix.utils.DyeColor;

public class ItemCyanDye extends ItemDye {
    public ItemCyanDye() {
        super(CYAN_DYE);
    }

    @Override
    public DyeColor getDyeColor() {
        return DyeColor.CYAN;
    }
}