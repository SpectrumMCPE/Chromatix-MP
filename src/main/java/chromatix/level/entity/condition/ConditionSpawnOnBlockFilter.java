package chromatix.level.entity.condition;

import chromatix.block.Block;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class ConditionSpawnOnBlockFilter extends Condition {

    public final ObjectArrayList<String> blockIds;

    public ConditionSpawnOnBlockFilter(String... blockIds) {
        super("minecraft:spawns_on_block_filter");
        this.blockIds = new ObjectArrayList<>(blockIds);
    }

    @Override
    public boolean evaluate(Block block) {
        return blockIds.contains(block.down().getId());
    }
}
