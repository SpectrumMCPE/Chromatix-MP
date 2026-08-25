package chromatix.item.enchantment.loot;

import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EnchantmentLootFishing extends EnchantmentLoot {
    public EnchantmentLootFishing() {
        super(Enchantment.ID_FORTUNE_FISHING, "lootBonusFishing", Rarity.RARE, EnchantmentType.FISHING_ROD);
    }
}
