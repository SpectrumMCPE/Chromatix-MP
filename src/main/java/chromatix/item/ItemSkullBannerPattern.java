package chromatix.item;

import chromatix.network.protocol.types.BannerPatternType;

public class ItemSkullBannerPattern extends ItemBannerPattern {
    public ItemSkullBannerPattern() {
        super(SKULL_BANNER_PATTERN);
    }

    @Override
    public BannerPatternType getPatternType() {
        return BannerPatternType.SKULL;
    }

    @Override
    public void setDamage(int damage) {
    }
}