package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;

public class SpawnRuleFox extends SpawnRule {

    public SpawnRuleFox() {
        super(Entity.FOX, 2, 4, 8,
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionBrightnessFilter(7, 15),
                new ConditionBiomeFilter(BiomeTags.TAIGA, BiomeTags.GROVE),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
