package chromatix.level.generator.feature.tree;

import chromatix.level.generator.feature.MultiFeatureWrapper;
import chromatix.level.generator.feature.decoration.DeadBushFeature;
import chromatix.level.generator.feature.decoration.MesaFoliageFeature;

public class MesaPlateauStoneTreeFeature extends MultiFeatureWrapper {

    public static final String NAME = "minecraft:mesa_plateau_stone_surface_trees_feature";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    protected String[] getFeatures() {
        return new String[] {
                DeadBushFeature.NAME,
                MesaFoliageFeature.NAME,
                MesaTreeFeature.NAME
        };
    }
}
