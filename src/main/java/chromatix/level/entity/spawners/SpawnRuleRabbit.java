package chromatix.level.entity.spawners;

import chromatix.block.BlockID;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;
import chromatix.utils.Utils;

public class SpawnRuleRabbit extends MultiSpawnRule {

    public SpawnRuleRabbit() {
        super(new Condition[]{
                new ConditionInAir(),
                new ConditionSpawnOnBlockFilter(Utils.concatArray(BlockTags.getBlockSet(BlockTags.GRASS).toArray(String[]::new),
                        BlockTags.getBlockSet(BlockTags.SAND).toArray(String[]::new),
                        new String[] {BlockID.SNOW, BlockID.SNOW_LAYER})),
                new ConditionSpawnOnGround(),
        }, new SpawnRuleRabbitTaigaOrSnow(), new SpawnRuleRabbitDesert(), new SpawnRuleRabbitFlowerOrSlopes(), new SpawnRuleRabbitGrove(), new SpawnRuleRabbitMeadow());
    }

    private static class SpawnRuleRabbitTaigaOrSnow extends SpawnRule {

        public SpawnRuleRabbitTaigaOrSnow() {
            super(Entity.RABBIT, 2 ,3, 4,
                    new ConditionAny(
                            new ConditionBiomeFilter(BiomeTags.FROZEN),
                            new ConditionAll(new ConditionBiomeFilter(BiomeTags.TAIGA), new ConditionNot(new ConditionBiomeFilter(BiomeTags.MEGA)))
                    ),
                    new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
            );
        }
    }

    private static class SpawnRuleRabbitDesert extends SpawnRule {

        public SpawnRuleRabbitDesert() {
            super(Entity.RABBIT, 2, 3, 12,
                    new ConditionBiomeFilter(BiomeTags.DESERT),
                    new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
            );
        }
    }

    private static class SpawnRuleRabbitFlowerOrSlopes extends SpawnRule {

        public SpawnRuleRabbitFlowerOrSlopes() {
            super(Entity.RABBIT, 2, 3, 4,
                    new ConditionBiomeFilter(BiomeTags.FLOWER_FOREST, BiomeTags.SNOWY_SLOPES),
                    new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
            );
        }
    }

    private static class SpawnRuleRabbitGrove extends SpawnRule {

        public SpawnRuleRabbitGrove() {
            super(Entity.RABBIT, 2, 3, 8,
                    new ConditionBiomeFilter(BiomeTags.GROVE),
                    new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
            );
        }
    }

    private static class SpawnRuleRabbitMeadow extends SpawnRule {

        public SpawnRuleRabbitMeadow() {
            super(Entity.RABBIT, 2, 6, 2,
                    new ConditionBiomeFilter(BiomeTags.MEADOW, BiomeTags.CHERRY_GROVE),
                    new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
            );
        }
    }

}
