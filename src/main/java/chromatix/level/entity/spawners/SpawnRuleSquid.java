package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionAll;
import chromatix.level.entity.condition.ConditionAny;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;
import chromatix.tags.BiomeTags;

public class SpawnRuleSquid extends SpawnRule {

    public SpawnRuleSquid() {
        super(Entity.SQUID, 2, 4, 8,
                new ConditionSpawnUnderwater(),
                new ConditionAny(
                        new ConditionAll(
                                new ConditionBiomeFilter(BiomeTags.OCEAN),
                                new ConditionDensityLimit(Entity.SQUID, 4, 128)
                        ),
                        new ConditionAll(
                                new ConditionBiomeFilter(BiomeTags.RIVER),
                                new ConditionDensityLimit(Entity.SQUID, 2, 64)
                        )
                ),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
