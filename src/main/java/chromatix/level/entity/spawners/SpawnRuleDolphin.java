package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionHeightFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionNot;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRuleDolphin extends SpawnRule {

    public SpawnRuleDolphin() {
        super(Entity.DOLPHIN, 3, 5, 7,
                new ConditionInAir(),
                new ConditionSpawnUnderwater(),
                new ConditionHeightFilter(0, 64),
                new ConditionDensityLimit(Entity.DOLPHIN, 5, 128),
                new ConditionBiomeFilter(BiomeTags.OCEAN),
                new ConditionNot(new ConditionBiomeFilter(BiomeTags.FROZEN)),
                new ConditionPopulationControl(ConditionPopulationControl.Category.WATER_ANIMAL)
        );
    }

}
