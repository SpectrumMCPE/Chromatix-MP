package chromatix.entity.effect;

import chromatix.entity.Entity;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.event.entity.EntityRegainHealthEvent;

import java.awt.*;

public class EffectInstantHealth extends InstantEffect {

    public EffectInstantHealth() {
        super(EffectType.INSTANT_HEALTH, "%potion.heal", new Color(248, 36, 35));
    }

    @Override
    public void apply(Entity entity, double tickCount) {
        double amount = (4 << this.getAmplifier()) * tickCount;
        if (entity.isUndead()) {
            entity.attack(new EntityDamageEvent(entity, EntityDamageEvent.DamageCause.MAGIC, (float) amount));
        } else {
            entity.heal(new EntityRegainHealthEvent(entity, (float) amount, EntityRegainHealthEvent.CAUSE_MAGIC));
        }
    }
}
