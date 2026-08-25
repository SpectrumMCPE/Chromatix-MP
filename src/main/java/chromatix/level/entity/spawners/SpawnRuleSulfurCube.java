package chromatix.level.entity.spawners;

import chromatix.entity.Entity;
import chromatix.level.entity.condition.ConditionAny;
import chromatix.level.entity.condition.ConditionBiomeFilter;
import chromatix.level.entity.condition.ConditionDifficultyFilter;
import chromatix.level.entity.condition.ConditionPopulationControl;
import chromatix.level.entity.condition.ConditionSpawnOnSurface;
import chromatix.level.entity.condition.ConditionSpawnUnderground;
import chromatix.tags.BiomeTags;

public class SpawnRuleSulfurCube extends SpawnRule {

    public SpawnRuleSulfurCube() {
        super(Entity.SULFUR_CUBE, 2, 4, 150,
                new ConditionAny(new ConditionSpawnOnSurface(), new ConditionSpawnUnderground()),
                new ConditionDifficultyFilter(0, 3),
                new ConditionBiomeFilter(BiomeTags.SULFUR_CAVES),
                new ConditionPopulationControl(ConditionPopulationControl.Category.ANIMAL));
    }
}
