package chromatix.entity.ai.controller;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.CoreMemoryTypes;

/**
 * A movement controller that provides lift for flying creatures
 */


public class LiftController implements IController {
    @Override
    public boolean control(EntityIntelligent entity) {
        //add lift force
        if (entity.getMemoryStorage().get(CoreMemoryTypes.ENABLE_LIFT_FORCE))
            entity.motionY += entity.getGravity();
        return true;
    }
}
