package chromatix.item.enchantment.loot;

import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EnchantmentLootWeapon extends EnchantmentLoot {

    public EnchantmentLootWeapon() {
        super(Enchantment.ID_LOOTING, "lootBonus", Rarity.RARE, EnchantmentType.SWORD);
    }

}
