package chromatix.entity.ai.executor.coppergolem;

import chromatix.Player;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.executor.IBehaviorExecutor;
import chromatix.entity.mob.EntityCopperGolem;

/**
 * @author Buddelbubi (Chromatix)
 * @since 2026/07/24
 */
public class ChestInteractionFailExecutor implements IBehaviorExecutor {

    private static final int FAIL_ANIMATION_TICKS = 2 * 20;

    private int tick;

    @Override
    public boolean execute(EntityIntelligent entity) {
        tick++;
        return tick < FAIL_ANIMATION_TICKS;
    }

    @Override
    public void onStart(EntityIntelligent entity) {
        tick = 0;
    }

    @Override
    public void onStop(EntityIntelligent entity) {
        resetInteraction(entity);
    }

    @Override
    public void onInterrupt(EntityIntelligent entity) {
        resetInteraction(entity);
    }

    private void resetInteraction(EntityIntelligent entity) {
        if (entity instanceof EntityCopperGolem golem) {
            golem.setEnumEntityProperty(EntityCopperGolem.PROPERTIES[0].getIdentifier(), "none");
            golem.sendData(golem.getViewers().values().toArray(Player[]::new));
        }
    }
}
