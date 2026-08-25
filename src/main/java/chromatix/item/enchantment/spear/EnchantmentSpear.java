package chromatix.item.enchantment.spear;

import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

/**
 * @author xRookieFight
 * @since 09/01/2026
 */
public abstract class EnchantmentSpear extends Enchantment {
    public EnchantmentSpear(int id, String name, Rarity rarity) {
        super(id, name, rarity, EnchantmentType.SPEAR);
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 50;
    }
}
