package chromatix.item.enchantment.damage;

import chromatix.item.Item;
import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class EnchantmentDamage extends Enchantment {

    public enum TYPE {
        ALL,
        SMITE,
        ARTHROPODS
    }
    protected final TYPE damageType;

    protected EnchantmentDamage(int id, String name, Rarity rarity, TYPE type) {
        super(id, name, rarity, EnchantmentType.SWORD);
        this.damageType = type;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentDamage);
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.isAxe() || super.canEnchant(item);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String getName() {
        return "%enchantment.damage." + this.name;
    }

    @Override
    public boolean isMajor() {
        return true;
    }

}
