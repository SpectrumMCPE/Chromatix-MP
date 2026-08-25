package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionHeightFilter;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRulePufferfish extends SpawnRule {

    public SpawnRulePufferfish() {
        super(Entity.PUFFERFISH, 3, 5, 25,
                new ConditionSpawnUnderwater(),
                new ConditionHeightFilter(0, 64),
                new ConditionDensityLimit(Entity.PUFFERFISH, 20, 128),
                new ConditionBiomeFilter(BiomeTags.OCEAN, BiomeTags.WARM),
                new ConditionPopulationControl(ConditionPopulationControl.Category.WATER_ANIMAL)
        );
    }

}
