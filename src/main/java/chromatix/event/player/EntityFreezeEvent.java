package chromatix.event.player;

import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.event.entity.EntityEvent;

public class EntityFreezeEvent extends EntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public EntityFreezeEvent(Entity human) {
        this.entity = human;
    }
}
