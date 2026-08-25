package chromatix.level.particle;

import chromatix.block.Block;
import chromatix.math.Vector3;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;

public class CrackBlockParticle extends GenericParticle {

    public CrackBlockParticle(Vector3 pos, Block block) {
        super(pos, LevelEvent.PARTICLE_CRACK_BLOCK, block.getBlockState().blockStateHash());
    }
}