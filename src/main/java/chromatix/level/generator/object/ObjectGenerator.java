package chromatix.level.generator.object;

import chromatix.block.BlockID;
import chromatix.math.Vector3;
import chromatix.utils.random.RandomSourceProvider;

public abstract class ObjectGenerator implements BlockID {
    public abstract boolean generate(BlockManager level, RandomSourceProvider rand, Vector3 position);
}
