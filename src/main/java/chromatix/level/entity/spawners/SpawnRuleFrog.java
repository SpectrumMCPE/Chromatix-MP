package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;

public class SpawnRuleFrog extends SpawnRule {

    public SpawnRuleFrog() {
        super(Entity.FROG, 2, 5, 10,
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionBiomeFilter(BiomeTags.SWAMP, BiomeTags.MANGROVE_SWAMP),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
