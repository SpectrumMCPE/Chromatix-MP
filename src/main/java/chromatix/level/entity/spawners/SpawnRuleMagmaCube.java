package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionDifficultyFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;

public class SpawnRuleMagmaCube extends MultiSpawnRule {

    public SpawnRuleMagmaCube() {
        super(new Condition[]{
                new ConditionDifficultyFilter(),
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionPopulationControl(ConditionPopulationControl.Category.MONSTER)
        }, new SpawnRuleMagmaCubeLess(), new SpawnRuleMagmaCubeMany());
    }

    private static class SpawnRuleMagmaCubeLess extends SpawnRule {

        public SpawnRuleMagmaCubeLess() {
            super(Entity.MAGMA_CUBE, 1 ,4, 10,
                    new ConditionBiomeFilter(BiomeTags.SPAWN_MAGMA_CUBES),
                    new ConditionDensityLimit(Entity.MAGMA_CUBE, 4));
        }
    }

    private static class SpawnRuleMagmaCubeMany extends SpawnRule {

        public SpawnRuleMagmaCubeMany() {
            super(Entity.MAGMA_CUBE, 2, 5, 100,
                    new ConditionBiomeFilter(BiomeTags.SPAWN_MANY_MAGMA_CUBES),
                    new ConditionDensityLimit(Entity.MAGMA_CUBE, 5));
        }
    }

}
