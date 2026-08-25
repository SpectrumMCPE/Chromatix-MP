package chromatix.entity.ai.executor.armadillo;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.executor.EntityControl;
import chromatix.entity.ai.executor.IBehaviorExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.item.Item;
import chromatix.level.Sound;
import chromatix.utils.Utils;

public class ShedExecutor implements EntityControl, IBehaviorExecutor {

    public ShedExecutor() {}
    @Override
    public boolean execute(EntityIntelligent entity) {
        return false;
    }

    @Override
    public void onStart(EntityIntelligent entity) {
        entity.getLevel().dropItem(entity, Item.get(Item.ARMADILLO_SCUTE));
        entity.getLevel().addSound(entity, Sound.MOB_ARMADILLO_SCUTE_DROP);
        entity.getMemoryStorage().put(CoreMemoryTypes.NEXT_SHED, entity.getLevel().getTick() + Utils.rand(6_000, 10_800));
    }
}
