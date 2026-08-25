package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleHusk extends SpawnRule {

    public SpawnRuleHusk() {
        super(Entity.HUSK, 2, 4, 240,
                new ConditionInAir(),
                new ConditionDifficultyFilter(),
                new ConditionSpawnOnGround(),
                new ConditionBrightnessFilter(0, 7),
                new ConditionBiomeFilter(BiomeTags.DESERT),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

}
