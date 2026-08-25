package chromatix.level.generator.feature.tree;

import chromatix.block.property.enums.WoodType;
import chromatix.level.generator.feature.LegacyTreeGeneratorFeature;
import chromatix.level.generator.object.ObjectFallenTree;
import chromatix.level.generator.object.TreeGenerator;
import chromatix.level.generator.object.legacytree.LegacyBirchTree;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;

public class BirchForestTreeFeature extends LegacyTreeGeneratorFeature {

    public static final String NAME = "minecraft:birch_forest_surface_trees_feature";

    @Override
    public TreeGenerator getGenerator(RandomSourceProvider random) {
        return random.nextInt(100) == 0 ? new ObjectFallenTree(WoodType.BIRCH) : new LegacyBirchTree();
    }

    @Override
    public int getMin() {
        return 7;
    }

    @Override
    public int getMax() {
        return 8;
    }

    @Override
    public String getRequiredTag() {
        return BiomeTags.BIRCH;
    }

    @Override
    protected float getBeeNestChance() {
        return 0.00035F;
    }

    @Override
    public String name() {
        return NAME;
    }
}
