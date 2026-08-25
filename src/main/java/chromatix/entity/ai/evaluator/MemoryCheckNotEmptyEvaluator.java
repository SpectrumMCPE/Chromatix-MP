package chromatix.entity.ai.evaluator;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.MemoryType;


public class MemoryCheckNotEmptyEvaluator implements IBehaviorEvaluator {

    protected MemoryType<?> type;

    public MemoryCheckNotEmptyEvaluator(MemoryType<?> type) {
        this.type = type;
    }

    @Override
    public boolean evaluate(EntityIntelligent entity) {
        return entity.getBehaviorGroup().getMemoryStorage().notEmpty(type);
    }
}
