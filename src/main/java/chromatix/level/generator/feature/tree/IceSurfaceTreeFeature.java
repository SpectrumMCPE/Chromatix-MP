package chromatix.level.generator.feature.tree;

import chromatix.block.property.enums.WoodType;
import chromatix.level.generator.feature.ObjectGeneratorFeature;
import chromatix.level.generator.object.ObjectFallenTree;
import chromatix.level.generator.object.ObjectGenerator;
import chromatix.level.generator.object.ObjectSmallSpruceTree;
import chromatix.utils.random.RandomSourceProvider;

public class IceSurfaceTreeFeature extends ObjectGeneratorFeature {

    public static final String NAME = "minecraft:ice_surface_trees_feature";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public ObjectGenerator getGenerator(RandomSourceProvider random) {
        return random.nextInt(100) == 0 ? new ObjectFallenTree(WoodType.SPRUCE) : new ObjectSmallSpruceTree();
    }

    @Override
    public int getMin() {
        return -20;
    }

    @Override
    public int getMax() {
        return 1;
    }
}
