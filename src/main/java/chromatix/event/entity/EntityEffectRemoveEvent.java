package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.entity.effect.Effect;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


public class EntityEffectRemoveEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Effect removeEffect;

    public EntityEffectRemoveEvent(Entity entity, Effect effect) {
        this.entity = entity;
        this.removeEffect = effect;
    }

    public Effect getRemoveEffect() {
        return removeEffect;
    }

}
