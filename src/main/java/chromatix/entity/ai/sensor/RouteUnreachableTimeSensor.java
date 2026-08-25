package chromatix.entity.ai.sensor;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.MemoryType;


public class RouteUnreachableTimeSensor implements ISensor {

    protected MemoryType<Integer> type;

    public RouteUnreachableTimeSensor(MemoryType<Integer> type) {
        this.type = type;
    }

    @Override
    public void sense(EntityIntelligent entity) {
        var old = entity.getMemoryStorage().get(type);
        if (!entity.getBehaviorGroup().getRouteFinder().isReachable()) {
            entity.getMemoryStorage().put(type, old + 1);
        } else {
            entity.getMemoryStorage().put(type, 0);
        }
    }
}
