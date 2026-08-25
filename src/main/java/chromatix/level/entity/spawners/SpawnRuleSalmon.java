package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionHeightFilter;
import chromatix.level.entity.condition.ConditionNot;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRuleSalmon extends MultiSpawnRule {

    public SpawnRuleSalmon() {
        super(new Condition[]{
                new ConditionSpawnUnderwater(),
                new ConditionDensityLimit(Entity.SALMON, 20, 128),
                new ConditionPopulationControl(ConditionPopulationControl.Category.WATER_ANIMAL)
        }, new SpawnRuleSalmonOcean(), new SpawnRuleSalmonRiver());
    }

    private static class SpawnRuleSalmonOcean extends SpawnRule {
        public SpawnRuleSalmonOcean() {
            super(Entity.SALMON, 3, 5, 26,
                    new ConditionHeightFilter(0, 64),
                    new ConditionBiomeFilter(BiomeTags.OCEAN),
                    new ConditionNot(new ConditionBiomeFilter(BiomeTags.WARM)));
        }
    }

    private static class SpawnRuleSalmonRiver extends SpawnRule {
        public SpawnRuleSalmonRiver() {
            super(Entity.SALMON, 3, 5, 16,
                    new ConditionHeightFilter(50, 64),
                    new ConditionBiomeFilter(BiomeTags.RIVER));
        }
    }

}
