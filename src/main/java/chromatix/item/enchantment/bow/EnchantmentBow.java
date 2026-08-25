package chromatix.item.enchantment.bow;

import chromatix.entity.EntityLiving;
import chromatix.entity.projectile.EntityProjectile;
import chromatix.item.ItemBow;
import chromatix.item.enchantment.Enchantment;
import chromatix.item.enchantment.EnchantmentType;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class EnchantmentBow extends Enchantment {
    protected EnchantmentBow(int id, String name, Rarity rarity) {
        super(id, name, rarity, EnchantmentType.BOW);
    }

    /**
     * Called when the bow is shot
     *
     * @param user       the entity using the bow
     * @param projectile the arrow entity
     * @param bow        the bow item
     */
    public void onBowShoot(EntityLiving user, EntityProjectile projectile, ItemBow bow) {

    }
}
