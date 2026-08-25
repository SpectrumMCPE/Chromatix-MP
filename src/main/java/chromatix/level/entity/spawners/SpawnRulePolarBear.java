package chromatix.level.entity.spawners;

import chromatix.block.BlockID;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionNot;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;

public class SpawnRulePolarBear extends MultiSpawnRule {

    public SpawnRulePolarBear() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionBrightnessFilter(7, 15),
                new ConditionBiomeFilter(BiomeTags.FROZEN),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        }, new SpawnRulePolarBearLand(), new SpawnRulePolarBearOcean());
    }

    private static class SpawnRulePolarBearLand extends SpawnRule {
        public SpawnRulePolarBearLand() {
            super(Entity.POLAR_BEAR, 1, 2, 1,
                    new ConditionNot(new ConditionBiomeFilter(BiomeTags.OCEAN)));
        }
    }

    private static class SpawnRulePolarBearOcean extends SpawnRule {
        public SpawnRulePolarBearOcean() {
            super(Entity.POLAR_BEAR, 1, 2, 5,
                    new ConditionSpawnOnBlockFilter(BlockID.ICE),
                    new ConditionBiomeFilter(BiomeTags.OCEAN));
        }
    }

}
