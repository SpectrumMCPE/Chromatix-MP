package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.math.Vector3;

public class EntityMoveByPistonEvent extends EntityMotionEvent {
    public EntityMoveByPistonEvent(Entity entity, Vector3 pos) {
        super(entity, pos);
    }
}
