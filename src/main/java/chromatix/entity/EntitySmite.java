package chromatix.entity;

import chromatix.entity.effect.EffectType;
import chromatix.inventory.EntityInventoryHolder;
import chromatix.level.Level;

/**
 * This interface represents the monster entity of the undead class
 *
 * @author MagicDroidX (Nukkit Project)
 */
public interface EntitySmite {
    default void burn(Entity entity) {
        boolean noHelmet = true;

        if (entity instanceof EntityInventoryHolder holder) {
            var armor = holder.getArmorInventory();
            noHelmet = armor == null || armor.getHelmet().isNull();
        }

        if (entity.getLevel().getDimension() == Level.DIMENSION_OVERWORLD
                && entity.getLevel().isDaytime()
                && !entity.getLevel().isRaining()
                && !entity.hasEffect(EffectType.FIRE_RESISTANCE)
                && noHelmet
                && !entity.isInsideOfWater()
                && !entity.isUnderBlock()
                && !entity.isOnFire()) {
            entity.setOnFire(1);
        }
    }
}