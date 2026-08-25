package chromatix.level.generator.feature.tree;


import chromatix.level.generator.feature.LegacyTreeGeneratorFeature;
import chromatix.level.generator.object.ObjectFallenTree;
import chromatix.level.generator.object.TreeGenerator;
import chromatix.level.generator.object.legacytree.LegacyOakTree;
import chromatix.tags.BiomeTags;
import chromatix.utils.random.RandomSourceProvider;

public class MesaTreeFeature extends LegacyTreeGeneratorFeature {

    public static final String NAME = "minecraft:mesa_tree_feature";

    @Override
    public TreeGenerator getGenerator(RandomSourceProvider random) {
        return random.nextInt(100) == 0 ? new ObjectFallenTree() : new LegacyOakTree();
    }

    @Override
    public String getRequiredTag() {
        return BiomeTags.MESA;
    }

    @Override
    public String name() {
        return NAME;
    }
}
