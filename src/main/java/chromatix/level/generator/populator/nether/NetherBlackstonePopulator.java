package chromatix.level.generator.populator.nether;

import chromatix.block.BlockBlackstone;
import chromatix.block.BlockState;

public class NetherBlackstonePopulator extends NetherGravelPopulator {

    public static final String NAME = "nether_blackstone";

    protected static final BlockState STATE = BlockBlackstone.PROPERTIES.getDefaultState();
    @Override
    public BlockState getState(BlockState original) {
        return STATE;
    }

    @Override
    public String name() {
        return NAME;
    }

}
