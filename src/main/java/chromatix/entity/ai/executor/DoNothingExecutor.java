package chromatix.entity.ai.executor;

import chromatix.entity.EntityIntelligent;

public class DoNothingExecutor implements IBehaviorExecutor {

    public DoNothingExecutor() {
    }
    @Override
    public boolean execute(EntityIntelligent entity) {
        return true;
    }
}
