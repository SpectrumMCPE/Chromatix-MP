package chromatix.level.entity.spawners;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionNot;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;

public class SpawnRulePiglin extends MultiSpawnRule {

    public SpawnRulePiglin() {
        super(new Condition[]{
                new ConditionBiomeFilter(BiomeTags.SPAWN_PIGLIN, BiomeTags.SPAWN_FEW_PIGLINS),
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionNot(new ConditionSpawnOnBlockFilter(Block.NETHER_WART_BLOCK, Block.SHROOMLIGHT)),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        }, new SpawnRulePiglinLess(), new SpawnRulePiglinFew());
    }

    private static class SpawnRulePiglinLess extends SpawnRule {

        public SpawnRulePiglinLess() {
            super(Entity.PIGLIN, 2 ,4, 5,
                    new ConditionBiomeFilter(BiomeTags.SPAWN_PIGLIN));
        }
    }

    private static class SpawnRulePiglinFew extends SpawnRule {

        public SpawnRulePiglinFew() {
            super(Entity.PIGLIN, 4, 4, 15,
                    new ConditionBiomeFilter(BiomeTags.SPAWN_FEW_PIGLINS));
        }
    }

}
