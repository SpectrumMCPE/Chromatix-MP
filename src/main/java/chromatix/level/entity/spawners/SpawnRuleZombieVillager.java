package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleZombieVillager extends SpawnRule {

    public SpawnRuleZombieVillager() {
        super(Entity.ZOMBIE_VILLAGER_V2, 2, 4, 5,
                new ConditionInAir(),
                new ConditionDifficultyFilter(),
                new ConditionSpawnOnGround(),
                new ConditionBrightnessFilter(0, 7),
                new ConditionBiomeFilter(BiomeTags.MONSTER),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

}
