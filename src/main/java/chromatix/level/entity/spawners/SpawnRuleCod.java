package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionHeightFilter;
import chromatix.level.entity.condition.ConditionNot;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRuleCod extends SpawnRule {

    public SpawnRuleCod() {
        super(Entity.COD, 4, 7, 75,
                new ConditionSpawnUnderwater(),
                new ConditionHeightFilter(0, 64),
                new ConditionDensityLimit(Entity.COD, 20, 128),
                new ConditionBiomeFilter(BiomeTags.OCEAN),
                new ConditionNot(new ConditionBiomeFilter(BiomeTags.WARM)),
                new ConditionPopulationControl(ConditionPopulationControl.Category.WATER_ANIMAL)
        );
    }

}
