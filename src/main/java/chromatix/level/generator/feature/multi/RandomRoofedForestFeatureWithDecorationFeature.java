package chromatix.level.generator.feature.multi;

import chromatix.level.generator.feature.MultiFeatureWrapper;
import chromatix.level.generator.feature.decoration.ForestFoliageFeature;
import chromatix.level.generator.feature.decoration.HugeMushroomFeature;
import chromatix.level.generator.feature.tree.RoofedForestTreeFeature;

public class RandomRoofedForestFeatureWithDecorationFeature extends MultiFeatureWrapper {

    public static final String NAME = "minecraft:random_roofed_forest_feature_with_decoration_feature";

    @Override
    protected String[] getFeatures() {
        return new String[] {
                ForestFoliageFeature.NAME,
                HugeMushroomFeature.NAME,
                RoofedForestTreeFeature.NAME
        };
    }

    @Override
    public String name() {
        return NAME;
    }
}
