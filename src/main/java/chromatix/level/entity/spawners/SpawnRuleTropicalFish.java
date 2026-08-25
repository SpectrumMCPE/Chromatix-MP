package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRuleTropicalFish extends SpawnRule {

    public SpawnRuleTropicalFish() {
        super(Entity.TROPICALFISH, 3, 5, 75,
                new ConditionSpawnUnderwater(),
                new ConditionDensityLimit(Entity.TROPICALFISH, 20, 128),
                new ConditionBiomeFilter(BiomeTags.OCEAN),
                new ConditionBiomeFilter(BiomeTags.WARM, BiomeTags.LUKEWARM),
                new ConditionPopulationControl(ConditionPopulationControl.Category.WATER_ANIMAL)
        );
    }

}
