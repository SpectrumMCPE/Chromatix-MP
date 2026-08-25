package chromatix.item.enchantment.crossbow;

import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;


public abstract class EnchantmentCrossbow extends Enchantment {


    protected EnchantmentCrossbow(int id, String name, Rarity rarity) {
        super(id, name, rarity, EnchantmentType.CROSSBOW);
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 50;
    }
}
