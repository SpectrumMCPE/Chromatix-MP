package chromatix.level.entity.spawners;

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
import chromatix.tags.BlockTags;

public class SpawnRulePanda extends MultiSpawnRule {

    public SpawnRulePanda() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new)),
                new ConditionBrightnessFilter(7, 15),
                new ConditionBiomeFilter(BiomeTags.JUNGLE),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        }, new SpawnRulePandaJungle(), new SpawnRulePandaBamboo());
    }

    private static class SpawnRulePandaJungle extends SpawnRule {
        public SpawnRulePandaJungle() {
            super(Entity.PANDA, 1, 2, 10,
                    new ConditionNot(new ConditionBiomeFilter(BiomeTags.BAMBOO)));
        }
    }

    private static class SpawnRulePandaBamboo extends SpawnRule {
        public SpawnRulePandaBamboo() {
            super(Entity.PANDA, 1, 2, 40,
                    new ConditionBiomeFilter(BiomeTags.BAMBOO));
        }
    }

}
