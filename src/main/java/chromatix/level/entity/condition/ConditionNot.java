package chromatix.level.entity.condition;

import chromatix.block.Block;

public class ConditionNot extends Condition {

    public final Condition condition;

    public ConditionNot(Condition condition) {
        super("pnx:not");
        this.condition = condition;
    }

    @Override
    public boolean evaluate(Block block) {
        return !condition.evaluate(block);
    }
}
