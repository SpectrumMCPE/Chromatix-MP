package chromatix.level.entity.condition;

import chromatix.block.Block;
import chromatix.block.BlockLiquid;

public class ConditionInAir extends Condition {

    public ConditionInAir() {
        super("pnx:in_air");
    }

    @Override
    public boolean evaluate(Block block) {
        return block.canPassThrough() && !(block instanceof BlockLiquid);
    }
}
