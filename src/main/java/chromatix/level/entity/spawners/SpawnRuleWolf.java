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
import chromatix.tags.BlockTags;
import chromatix.utils.Utils;

public class SpawnRuleWolf extends MultiSpawnRule {

    public SpawnRuleWolf() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionBrightnessFilter(7, 15),
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(Utils.concatArray(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new),
                        BlockTags.getBlockSet(BlockTags.DIRT).toArray(String[]::new),
                        new String[] {BlockID.PODZOL})),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        }, new SpawnRuleMagmaCubeLess(), new SpawnRuleMagmaCubeMany());
    }

    private static class SpawnRuleMagmaCubeLess extends SpawnRule {

        public SpawnRuleMagmaCubeLess() {
            super(Entity.WOLF, 4 ,4, 8,
                    new ConditionBiomeFilter(BiomeTags.TAIGA));
        }
    }

    private static class SpawnRuleMagmaCubeMany extends SpawnRule {

        public SpawnRuleMagmaCubeMany() {
            super(Entity.WOLF, 2, 4, 5,
                    new ConditionBiomeFilter(BiomeTags.FOREST),
                    new ConditionNot(new ConditionBiomeFilter(BiomeTags.MUTATED, BiomeTags.BIRCH, BiomeTags.ROOFED, BiomeTags.MOUNTAIN))
            );
        }
    }

}
