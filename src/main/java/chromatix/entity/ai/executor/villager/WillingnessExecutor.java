package chromatix.entity.ai.executor.villager;

import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.executor.EntityControl;
import chromatix.entity.ai.executor.IBehaviorExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.passive.EntityVillagerV2;
import chromatix.item.Item;
import chromatix.item.ItemFood;


public class WillingnessExecutor implements EntityControl, IBehaviorExecutor {


    public WillingnessExecutor() {}
    @Override
    public boolean execute(EntityIntelligent entity) {
        return false;
    }

    @Override
    public void onStart(EntityIntelligent entity) {
        if(entity instanceof EntityVillagerV2 villager) {
            for(int j = 0; j < villager.getInventory().getSize(); j++) {
                Item item = villager.getInventory().getItem(j);
                if(item instanceof ItemFood) {
                    villager.getInventory().clear(j);
                }
            }
        }
        entity.getMemoryStorage().put(CoreMemoryTypes.WILLING, true);
    }
}
