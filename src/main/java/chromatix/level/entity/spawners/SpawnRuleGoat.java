package chromatix.level.entity.spawners;

import chromatix.block.BlockID;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.*;
import chromatix.tags.BiomeTags;

public class SpawnRuleGoat extends SpawnRule {

    public SpawnRuleGoat() {
        super(Entity.GOAT, 1, 3, 5,
                new ConditionSpawnOnGround(),
                new ConditionSpawnOnBlockFilter(BlockID.STONE, BlockID.SNOW, BlockID.POWDER_SNOW, BlockID.SNOW_LAYER, BlockID.PACKED_ICE, BlockID.GRAVEL),
                new ConditionBrightnessFilter(7, 15),
                new ConditionBiomeFilter(BiomeTags.SNOWY_SLOPES, BiomeTags.JAGGED_PEAKS, BiomeTags.FROZEN_PEAKS),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
