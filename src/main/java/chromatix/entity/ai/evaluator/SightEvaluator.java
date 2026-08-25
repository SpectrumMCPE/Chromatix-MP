package chromatix.entity.ai.evaluator;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.MemoryType;
import chromatix.math.Vector3;

public class SightEvaluator implements IBehaviorEvaluator {

    private final MemoryType<? extends Entity> type;

    public SightEvaluator(MemoryType<? extends Entity> type) {
        this.type = type;
    }

    @Override
    public boolean evaluate(EntityIntelligent entity) {
        if (entity.getMemoryStorage().isEmpty(type)) {
            return false;
        }
        return entity.hasLineOfSight(entity.getMemoryStorage().get(type));
    }
}
