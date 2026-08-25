package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.event.Event;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class EntityEvent extends Event {

    protected Entity entity;

    public Entity getEntity() {
        return entity;
    }
}
