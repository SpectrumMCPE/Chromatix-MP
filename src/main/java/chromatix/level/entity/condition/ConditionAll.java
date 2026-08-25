package chromatix.level.entity.condition;

import chromatix.block.Block;

import java.util.Arrays;

public class ConditionAll extends Condition {

    public final Condition[] conditions;

    public ConditionAll(Condition... condition) {
        super("pnx:all");
        this.conditions = condition;
    }

    @Override
    public boolean evaluate(Block block) {
        return Arrays.stream(conditions).allMatch(condition -> condition.evaluate(block));
    }
}
