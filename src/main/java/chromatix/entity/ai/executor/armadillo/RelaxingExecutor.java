package chromatix.entity.ai.executor.armadillo;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.executor.EntityControl;
import chromatix.entity.ai.executor.IBehaviorExecutor;
import chromatix.entity.passive.EntityArmadillo;


public class RelaxingExecutor implements EntityControl, IBehaviorExecutor {


    public RelaxingExecutor() {}
    @Override
    public boolean execute(EntityIntelligent entity) {
        return false;
    }

    @Override
    public void onStart(EntityIntelligent entity) {
        removeLookTarget(entity);
        removeRouteTarget(entity);
        if(entity instanceof EntityArmadillo armadillo) {
            armadillo.setRollState(EntityArmadillo.RollState.ROLLED_UP_RELAXING);
        }
    }

}
