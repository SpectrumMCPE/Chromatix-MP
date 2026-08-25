package chromatix.level.entity.condition;

import chromatix.block.Block;

public class ConditionDisallowSpawnInBubble extends Condition{

    public ConditionDisallowSpawnInBubble() {
        super("minecraft:disallow_spawns_in_bubble");
    }

    @Override
    public boolean evaluate(Block block) {
        return !block.getLevel().getBlock(block, 1).getId().equals(Block.BUBBLE_COLUMN);
    }
}
