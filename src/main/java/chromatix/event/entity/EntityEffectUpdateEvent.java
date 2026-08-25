package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.entity.effect.Effect;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


public class EntityEffectUpdateEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Effect oldEffect;
    private Effect newEffect;

    public EntityEffectUpdateEvent(Entity entity, Effect oldEffect, Effect newEffect) {
        this.entity = entity;
        this.oldEffect = oldEffect;
        this.newEffect = newEffect;
    }

    public Effect getOldEffect() {
        return this.oldEffect;
    }

    public Effect getNewEffect() {
        return this.newEffect;
    }
}
