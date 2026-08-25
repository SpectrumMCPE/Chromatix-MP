package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionDensityLimit;
import chromatix.level.entity.condition.ConditionHeightFilter;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnUnderground;
import chromatix.level.entity.condition.ConditionSpawnUnderwater;

public class SpawnRuleGlowSquid extends SpawnRule {

    public SpawnRuleGlowSquid() {
        super(Entity.GLOW_SQUID, 2, 4, 10,
                new ConditionSpawnUnderwater(),
                new ConditionSpawnUnderground(),
                new ConditionHeightFilter(-64, 30),
                new ConditionDensityLimit(Entity.GLOW_SQUID, 2, 128),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL)
        );
    }

}
