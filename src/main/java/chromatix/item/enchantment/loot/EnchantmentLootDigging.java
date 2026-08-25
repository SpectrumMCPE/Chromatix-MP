package chromatix.item.enchantment.loot;

import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EnchantmentLootDigging extends EnchantmentLoot {
    public EnchantmentLootDigging() {
        super(Enchantment.ID_FORTUNE_DIGGING, "lootBonusDigger", Rarity.RARE, EnchantmentType.DIGGER);
    }
}
