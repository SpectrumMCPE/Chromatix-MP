package chromatix.level.entity.condition;

import chromatix.block.Block;
import chromatix.block.BlockLeaves;

public class ConditionSpawnOnGround extends Condition {

    private final boolean allowWater;

    public ConditionSpawnOnGround() {
        this(false);
    }

    public ConditionSpawnOnGround(boolean allowWater) {
        super("pnx:spawns_on_ground");
        this.allowWater = allowWater;
    }

    @Override
    public boolean evaluate(Block block) {
        return block.getLevel().standable(block, allowWater, false) && !(block.down() instanceof BlockLeaves);
    }
}
