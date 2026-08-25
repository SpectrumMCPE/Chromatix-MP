package chromatix.level.entity.condition;

import chromatix.Server;
import chromatix.block.Block;

public class ConditionDifficultyFilter extends Condition {

    public final int min, max;

    public ConditionDifficultyFilter() {
        this(1, 3);
    }

    public ConditionDifficultyFilter(int min, int max) {
        super("minecraft:difficulty_filter");
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean evaluate(Block block) {
        int difficulty = Server.getInstance().getDifficulty();
        return difficulty >= min && difficulty <= max;
    }
}
