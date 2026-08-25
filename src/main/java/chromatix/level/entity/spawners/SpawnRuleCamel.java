package chromatix.level.entity.spawners;

import chromatix.block.BlockID;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;

public class SpawnRuleCamel extends SpawnRule {

    public SpawnRuleCamel() {
        super(Entity.CAMEL, 1,
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockID.SAND, BlockID.RED_SAND, BlockID.SANDSTONE),
                new ConditionBrightnessFilter(7, 15),
                new ConditionBiomeFilter(BiomeTags.DESERT),
                new ConditionDensityLimit(Entity.CAMEL, 1, 128),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL));
    }

}
