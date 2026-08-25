package chromatix.item.enchantment.protection;

import chromatix.event.entity.EntityDamageEvent;
import chromatix.event.entity.EntityDamageEvent.DamageCause;
import chromatix.item.enchantment.Enchantment;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EnchantmentProtectionAll extends EnchantmentProtection {

    public EnchantmentProtectionAll() {
        super(Enchantment.ID_PROTECTION_ALL, "all", Rarity.COMMON, TYPE.ALL);
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 1 + (level - 1) * 11;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 11;
    }

    @Override
    public double getTypeModifier() {
        return 1;
    }

    @Override
    public float getProtectionFactor(EntityDamageEvent e) {
        DamageCause cause = e.getCause();

        if (level <= 0 || cause == DamageCause.VOID || cause == DamageCause.CUSTOM || cause == DamageCause.MAGIC || cause == DamageCause.HUNGER) {
            return 0;
        }

        return (float) (getLevel() * getTypeModifier());
    }
}
