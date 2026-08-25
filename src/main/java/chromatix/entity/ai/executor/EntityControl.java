package chromatix.entity.ai.executor;

import chromatix.entity.EntityIntelligent;
import chromatix.math.Vector3;
import org.jetbrains.annotations.NotNull;

/**
 * Involving some methods about controller.
 */


public interface EntityControl {

    default void setRouteTarget(@NotNull EntityIntelligent entity, Vector3 vector3) {
        entity.setMoveTarget(vector3);
    }

    default void setLookTarget(@NotNull EntityIntelligent entity, Vector3 vector3) {
        entity.setLookTarget(vector3);
    }

    default void removeRouteTarget(@NotNull EntityIntelligent entity) {
        entity.setMoveTarget(null);
    }

    default void removeLookTarget(@NotNull EntityIntelligent entity) {
        entity.setLookTarget(null);
    }
}
