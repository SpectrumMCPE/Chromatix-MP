package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.level.Level;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class EntityLevelChangeEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Level originLevel;
    private final Level targetLevel;

    public EntityLevelChangeEvent(Entity entity, Level originLevel, Level targetLevel) {
        this.entity = entity;
        this.originLevel = originLevel;
        this.targetLevel = targetLevel;
    }

    public Level getOrigin() {
        return originLevel;
    }

    public Level getTarget() {
        return targetLevel;
    }
}
