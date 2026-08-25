package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionHeightFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

public class SpawnRuleTurtle extends SpawnRule {

    public SpawnRuleTurtle() {
        super(Entity.TURTLE, 2, 6, 8,
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockTags.getBlockSet(BlockTags.SAND).toArray(String[]::new)),
                new ConditionBrightnessFilter(7, 15),
                new ConditionHeightFilter(60, 67),
                new ConditionBiomeFilter(BiomeTags.BEACH),
                new ConditionBiomeFilter(BiomeTags.WARM),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
