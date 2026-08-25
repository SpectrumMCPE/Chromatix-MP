package chromatix.entity.effect;

import chromatix.Player;
import chromatix.entity.Entity;

import java.awt.*;

public class EffectSaturation extends InstantEffect {

    public EffectSaturation() {
        super(EffectType.SATURATION, "%potion.saturation", new Color(248, 36, 35));
    }

    @Override
    public void apply(Entity entity, double tickCount) {
        if (entity instanceof Player player) {
            player.getFoodData().addFood(this.getLevel(), 2 * this.getLevel());
        }
    }
}
