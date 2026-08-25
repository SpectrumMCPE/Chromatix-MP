package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleWitch extends SpawnRule {

    public SpawnRuleWitch() {
        super(Entity.WITCH, 5,
                new ConditionInAir(),
                new ConditionDifficultyFilter(),
                new ConditionSpawnOnGround(),
                new ConditionBrightnessFilter(0, 7),
                new ConditionBiomeFilter(BiomeTags.MONSTER),
                new ConditionDensityLimit(Entity.WITCH, 1, 128),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

}
