package chromatix.item;

import chromatix.network.protocol.types.BannerPatternType;

public class ItemCreeperBannerPattern extends ItemBannerPattern {
    public ItemCreeperBannerPattern() {
        super(CREEPER_BANNER_PATTERN);
    }

    @Override
    public void setDamage(int meta) {
    }

    @Override
    public BannerPatternType getPatternType() {
        return BannerPatternType.CREEPER;
    }
}