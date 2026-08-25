package chromatix.level.entity.spawners;

import chromatix.block.BlockID;
import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnBlockFilter;
import chromatix.level.entity.condition.ConditionSpawnUnderground;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRuleAxolotl extends SpawnRule {

    public SpawnRuleAxolotl() {
        super(Entity.AXOLOTL, 4, 6, 10,
                new ConditionSpawnUnderground(),
                new ConditionSpawnUnderwater(),
                //new ConditionDisallowSpawnInBubble(), //Will never happen since spawning on clay is required
                new ConditionSpawnOnBlockFilter(BlockID.CLAY),
                new ConditionBiomeFilter(BiomeTags.LUSH_CAVES),
                new ConditionPopulationControl(ConditionPopulationControl.Category.WATER_ANIMAL),
                new ConditionDensityLimit(Entity.AXOLOTL,5));
    }

}
