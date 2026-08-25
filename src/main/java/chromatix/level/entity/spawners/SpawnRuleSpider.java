package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleSpider extends SpawnRule {

    public SpawnRuleSpider() {
        super(Entity.SPIDER, 100,
                new ConditionInAir(),
                new ConditionDifficultyFilter(),
                new ConditionSpawnOnGround(),
                new ConditionBrightnessFilter(0, 7),
                new ConditionBiomeFilter(BiomeTags.MONSTER),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

}
