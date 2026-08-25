package chromatix.level.generator.object;

import chromatix.block.Block;
import chromatix.block.BlockBamboo;
import chromatix.block.BlockState;
import chromatix.level.Level;

public class GeneratorRoot extends BlockManager {

    public GeneratorRoot(Level level) {
        super(level);
    }

    public boolean canReplace(int x, int y, int z) {
        Block cached = getCachedBlock(x, y, z);
        if(cached == null) return true;
        return (cached.canBeReplaced() || cached.canPassThrough() || cached.diffusesSkyLight()) && !(cached instanceof BlockBamboo);
    }

    @Override
    public void setBlockStateAt(int x, int y, int z, BlockState state) {
        if(canReplace(x, y, z)) {
            super.setBlockStateAt(x, y, z, state);
        }
    }
}
