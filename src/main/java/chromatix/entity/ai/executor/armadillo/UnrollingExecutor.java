package chromatix.entity.ai.executor.armadillo;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.executor.EntityControl;
import chromatix.entity.ai.executor.IBehaviorExecutor;
import chromatix.entity.passive.EntityArmadillo;

public class UnrollingExecutor implements EntityControl, IBehaviorExecutor {

    protected int tick = 0;

    private static final int STAY_TICKS = 20;


    public UnrollingExecutor() {}
    @Override
    public boolean execute(EntityIntelligent entity) {
        if(tick < STAY_TICKS) {
            tick++;
            return true;
        }
        return false;
    }

    @Override
    public void onStart(EntityIntelligent entity) {
        this.tick = 0;
    }

    @Override
    public void onStop(EntityIntelligent entity) {
        if(entity instanceof EntityArmadillo armadillo) {
            armadillo.setRollState(EntityArmadillo.RollState.UNROLLED);
        }
    }

    @Override
    public void onInterrupt(EntityIntelligent entity) {
        onStop(entity);
    }
}
