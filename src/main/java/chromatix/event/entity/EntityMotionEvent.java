package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.math.Vector3;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EntityMotionEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Vector3 motion;

    public EntityMotionEvent(Entity entity, Vector3 motion) {
        this.entity = entity;
        this.motion = motion;
    }

    public Vector3 getMotion() {
        return this.motion;
    }
}
