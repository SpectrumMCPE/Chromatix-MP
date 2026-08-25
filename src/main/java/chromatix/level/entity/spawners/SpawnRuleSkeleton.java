package chromatix.level.entity.spawners;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleSkeleton extends SpawnRule {

    public SpawnRuleSkeleton() {
        super(Entity.SKELETON, 1, 2, 80,
                new ConditionInAir(),
                new ConditionDifficultyFilter(),
                new ConditionSpawnOnGround(),
                new ConditionNot(new ConditionSpawnOnBlockFilter(Block.NETHER_WART_BLOCK, Block.SHROOMLIGHT)),
                new ConditionBrightnessFilter(0, 7),
                new ConditionAny(
                        new ConditionAll(
                                new ConditionBiomeFilter(BiomeTags.MONSTER),
                                new ConditionNot(new ConditionBiomeFilter(BiomeTags.FROZEN))
                        ),
                        new ConditionBiomeFilter(BiomeTags.SOULSAND_VALLEY)
                ),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        );
    }

}
