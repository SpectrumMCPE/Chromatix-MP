package chromatix.level.generator.feature.decoration;

import chromatix.block.Block;
import chromatix.block.BlockClosedEyeblossom;
import chromatix.block.BlockState;
import chromatix.level.generator.object.BlockManager;
import chromatix.registry.Registries;
import chromatix.tags.BiomeTags;
import chromatix.tags.BlockTags;

public class EyeBlossomFeature extends SurfaceGenerateFeature {

    private static final BlockState CLOSED_EYEBLOSSOM = BlockClosedEyeblossom.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:scatter_eyeblossom_feature";

    @Override
    public void place(BlockManager manager, int x, int y, int z) {
        manager.setBlockStateAt(x, y, z, CLOSED_EYEBLOSSOM);
    }

    @Override
    public int getBase() {
        return 10;
    }

    @Override
    public int getRandom() {
        return 0;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean isSupportValid(Block support) {
        return support.hasTag(BlockTags.DIRT) && Registries.BIOME.containsTag(
                BiomeTags.PALE_GARDEN,
                support.getLevel().getBiomeId(support.getFloorX(), support.getFloorY(), support.getFloorZ())
        );
    }
}
