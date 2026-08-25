package chromatix.entity.ai.executor;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.mob.EntityShulker;
import chromatix.level.Sound;
import chromatix.utils.Utils;


public class ShulkerIdleExecutor implements IBehaviorExecutor {

    private int stayTicks = 0;
    private int tick = 0;

    public ShulkerIdleExecutor() {}
    @Override
    public boolean execute(EntityIntelligent entity) {
        return ++tick < stayTicks;
    }

    @Override
    public void onStart(EntityIntelligent entity) {
        tick = 0;
        stayTicks = Utils.rand(20, 61);
        if(entity instanceof EntityShulker shulker) {
            shulker.setPeeking(30);
            shulker.getLevel().addSound(shulker, Sound.MOB_SHULKER_OPEN);
        }
    }

    @Override
    public void onStop(EntityIntelligent entity) {
        if(entity instanceof EntityShulker shulker) {
            shulker.setPeeking(0);
            shulker.getLevel().addSound(shulker, Sound.MOB_SHULKER_CLOSE);
        }
    }

    @Override
    public void onInterrupt(EntityIntelligent entity) {
        onStop(entity);
    }
}
