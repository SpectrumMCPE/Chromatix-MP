package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

public class SpawnRuleParrot extends SpawnRule {

    public SpawnRuleParrot() {
        super(Entity.PARROT, 1, 2, 40,
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new)),
                new ConditionBrightnessFilter(7, 15),
                new ConditionBiomeFilter(BiomeTags.JUNGLE),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL));
    }

}
