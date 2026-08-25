package chromatix.entity.ai.executor;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.MemoryType;
import chromatix.math.Vector3;


public class LookAtTargetExecutor implements EntityControl, IBehaviorExecutor {

    //indicates which Memory the executor should get the target position from
    protected MemoryType<? extends Vector3> memory;
    protected int duration;
    protected int currentTick;

    public LookAtTargetExecutor(MemoryType<? extends Vector3> memory, int duration) {
        this.memory = memory;
        this.duration = duration;
    }

    @Override
    public boolean execute(EntityIntelligent entity) {
        currentTick++;
        if (!entity.isEnablePitch()) entity.setEnablePitch(true);
        var vector3Memory = entity.getMemoryStorage().get(memory);
        if (vector3Memory != null) {
            setLookTarget(entity, vector3Memory);
        }
        return currentTick <= duration;
    }

    @Override
    public void onInterrupt(EntityIntelligent entity) {
        currentTick = 0;
        entity.setEnablePitch(false);
    }

    @Override
    public void onStop(EntityIntelligent entity) {
        currentTick = 0;
        entity.setEnablePitch(false);
    }
}
