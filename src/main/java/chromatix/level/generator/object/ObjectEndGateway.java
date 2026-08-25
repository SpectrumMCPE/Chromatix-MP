package chromatix.level.generator.object;

import chromatix.block.BlockBedrock;
import chromatix.block.BlockEndGateway;
import chromatix.block.BlockState;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import chromatix.utils.random.RandomSourceProvider;

import java.util.Arrays;

public class ObjectEndGateway extends ObjectGenerator {

    protected static final BlockState BEDROCK = BlockBedrock.PROPERTIES.getDefaultState();
    protected static final BlockState END_GATEWAY = BlockEndGateway.PROPERTIES.getDefaultState();

    @Override
    public boolean generate(BlockManager level, RandomSourceProvider rand, Vector3 pos) {
        Arrays.stream(BlockFace.values()).forEach(face -> level.setBlockStateAt(pos.up().getSide(face), BEDROCK));
        Arrays.stream(BlockFace.values()).forEach(face -> level.setBlockStateAt(pos.down().getSide(face), BEDROCK));
        level.setBlockStateAt(pos, END_GATEWAY);
        return true;
    }
}
