package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleSlime extends SpawnRule {

    public SpawnRuleSlime() {
        super(Entity.SLIME, 100,
                new ConditionInAir(),
                new ConditionDifficultyFilter(),
                new ConditionSpawnOnGround(),
                new ConditionBiomeFilter(BiomeTags.SWAMP, BiomeTags.MANGROVE_SWAMP),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

}
