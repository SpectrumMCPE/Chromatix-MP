package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.Condition;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionBrightnessFilter;
import chromatix.level.entity.condition.ConditionInAir;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnOnGround;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

public class SpawnRuleSheep extends MultiSpawnRule {

    public SpawnRuleSheep() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new)),
                new ConditionBrightnessFilter(7, 15),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL),
        }, new SpawnRuleSheepDefault(), new SpawnRuleSheepMeadow());
    }

    private static class SpawnRuleSheepDefault extends SpawnRule {

        public SpawnRuleSheepDefault() {
            super(Entity.SHEEP, 2 ,3, 12,
                    new ConditionBiomeFilter(BiomeTags.ANIMAL)
            );
        }
    }

    private static class SpawnRuleSheepMeadow extends SpawnRule {

        public SpawnRuleSheepMeadow() {
            super(Entity.SHEEP, 2, 4, 2,
                    new ConditionBiomeFilter(BiomeTags.MEADOW, BiomeTags.CHERRY_GROVE));
        }
    }

}
