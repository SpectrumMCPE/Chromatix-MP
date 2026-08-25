package chromatix.level.entity.spawners;

import chromatix.block.BlockID;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionDifficultyFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionNot;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.level.entity.condition.ConditionSpawnUnderground;
import chromatix.tags.BiomeTags;

public class SpawnRuleHoglin extends SpawnRule {

    public SpawnRuleHoglin() {
        super(Entity.HOGLIN, 4, 4, 20,
                new ConditionSpawnOnGround(),
                new ConditionInAir(),
                new ConditionSpawnUnderground(),
                new ConditionDifficultyFilter(),
                new ConditionNot(new ConditionSpawnOnBlockFilter(BlockID.NETHER_WART_BLOCK, BlockID.SHROOMLIGHT)),
                new ConditionBiomeFilter(BiomeTags.CRIMSON_FOREST),
                new ConditionDensityLimit(Entity.HOGLIN, 4, 64),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
