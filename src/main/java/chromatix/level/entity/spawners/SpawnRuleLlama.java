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

public class SpawnRuleLlama extends MultiSpawnRule {

    public SpawnRuleLlama() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new)),
                new ConditionBrightnessFilter(7, 15),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        }, new SpawnRuleLlamaExtremeHills(), new SpawnRuleLlamaSavanna());
    }

    private static class SpawnRuleLlamaExtremeHills extends SpawnRule {

        public SpawnRuleLlamaExtremeHills() {
            super(Entity.LLAMA, 4 ,6, 5,
                    new ConditionBiomeFilter(BiomeTags.EXTREME_HILLS),
                    new ConditionDensityLimit(Entity.LLAMA, 6, 96));
        }
    }

    private static class SpawnRuleLlamaSavanna extends SpawnRule {

        public SpawnRuleLlamaSavanna() {
            super(Entity.LLAMA, 4, 4, 8,
                    new ConditionBiomeFilter(BiomeTags.SAVANNA),
                    new ConditionDensityLimit(Entity.LLAMA, 4, 96));
        }
    }

}
