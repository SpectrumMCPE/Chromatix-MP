package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

public class SpawnRuleDonkey extends MultiSpawnRule {

    public SpawnRuleDonkey() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new)),
                new ConditionBrightnessFilter(7, 15),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        }, new SpawnRuleDonkeyPlains(), new SpawnRuleDonkeyMeadow());
    }

    private static class SpawnRuleDonkeyPlains extends SpawnRule {

        public SpawnRuleDonkeyPlains() {
            super(Entity.DONKEY, 1 ,3, 1,
                    new ConditionBiomeFilter(BiomeTags.PLAINS),
                    new ConditionDensityLimit(Entity.DONKEY, 6)
            );
        }
    }

    private static class SpawnRuleDonkeyMeadow extends SpawnRule {

        public SpawnRuleDonkeyMeadow() {
            super(Entity.DONKEY, 1, 2, 1,
                    new ConditionBiomeFilter(BiomeTags.MEADOW),
                    new ConditionDensityLimit(Entity.DONKEY, 2)
            );
        }
    }

}
