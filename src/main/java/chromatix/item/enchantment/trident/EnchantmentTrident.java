package chromatix.item.enchantment.trident;

import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

public abstract class EnchantmentTrident extends Enchantment {
    protected EnchantmentTrident(int id, String name, Rarity rarity) {
        super(id, name, rarity, EnchantmentType.TRIDENT);
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 50;
    }
}
