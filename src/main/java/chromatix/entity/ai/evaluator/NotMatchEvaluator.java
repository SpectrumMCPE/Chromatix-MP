package chromatix.entity.ai.evaluator;

import chromatix.entity.EntityIntelligent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotMatchEvaluator implements IBehaviorEvaluator {

    private IBehaviorEvaluator evaluator;

    @Override
    public boolean evaluate(EntityIntelligent entity) {
        return !evaluator.evaluate(entity);
    }
}
