package chromatix.level.entity.condition;

import chromatix.block.Block;
import chromatix.utils.Utils;

public class ConditionProbability extends Condition {

    public final int factor, bounds;

    public ConditionProbability(int factor, int  bounds) {
        super("pnx:probability");
        this.factor = factor;
        this.bounds = bounds;
    }

    @Override
    public boolean evaluate(Block block) {
        return Utils.rand(0, bounds-1) < factor;
    }
}
