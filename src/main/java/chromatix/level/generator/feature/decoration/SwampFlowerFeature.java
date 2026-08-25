package chromatix.level.generator.feature.decoration;

import chromatix.block.Block;
import chromatix.block.BlockBlueOrchid;
import chromatix.block.BlockDeadbush;
import chromatix.block.BlockReeds;
import chromatix.block.BlockState;
import chromatix.tags.BlockTags;

public class SwampFlowerFeature extends GroupedDiscFeature {

    protected final static BlockState STATE = BlockBlueOrchid.PROPERTIES.getDefaultState();

    public static final String NAME = "minecraft:scatter_swamp_flower_feature";

    @Override
    public BlockState getSourceBlock() {
        return STATE;
    }

    @Override
    public int getMinRadius() {
        return 1;
    }

    @Override
    public int getMaxRadius() {
        return 2;
    }

    @Override
    public double getProbability() {
        return 0.7f;
    }

    @Override
    public int getBase() {
        return -10;
    }

    @Override
    public int getRandom() {
        return 12;
    }

    @Override
    public boolean isSupportValid(Block block) {
        return block.hasTag(BlockTags.DIRT) && BlockReeds.isSupportValid(block);
    }

    @Override
    public String name() {
        return NAME;
    }
}
