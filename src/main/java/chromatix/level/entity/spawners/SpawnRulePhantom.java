package chromatix.level.entity.spawners;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionDifficultyFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnSurface;
import chromatix.tags.BiomeTags;

public class SpawnRulePhantom extends SpawnRule {

    public SpawnRulePhantom() {
        super(Entity.PHANTOM, 100,
                new ConditionDifficultyFilter(),
                new ConditionNoSleep(),
                new ConditionInAir(),
                new ConditionSpawnOnSurface(),
                new ConditionBrightnessFilter(0, 7),
                new ConditionBiomeFilter(BiomeTags.MONSTER),
                new ConditionDensityLimit(Entity.PHANTOM, 1, 128),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

    private static class ConditionNoSleep extends Condition {

        public ConditionNoSleep() {
            super("pnx:phantom_no_sleep");
        }

        @Override
        public boolean evaluate(Block block) {
            return block.getLevel().noSleepNights >= 3;
        }
    }

}
