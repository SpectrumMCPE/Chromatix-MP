package chromatix.level.generator.feature.multi;

import chromatix.level.generator.feature.MultiFeatureWrapper;
import chromatix.level.generator.feature.decoration.MonsterRoomFeature;
import chromatix.level.generator.feature.terrain.CaveGenerateFeature;
import chromatix.level.generator.feature.terrain.CaveExtraUndergroundFeature;
import chromatix.level.generator.feature.terrain.CanyonCarverFeature;

public class OverworldCaveCarverFeature extends MultiFeatureWrapper {

    public static final String NAME = "minecraft:overworld_cave_carver_feature";

    @Override
    protected String[] getFeatures() {
        return new String[] {
                CaveGenerateFeature.NAME,
                CaveExtraUndergroundFeature.NAME,
                CanyonCarverFeature.NAME,
                MonsterRoomFeature.NAME
        };
    }

    @Override
    public String name() {
        return NAME;
    }
}
