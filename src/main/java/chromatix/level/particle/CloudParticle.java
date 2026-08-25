package chromatix.level.particle;

import chromatix.math.Vector3;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;


public class CloudParticle extends GenericParticle {

    public CloudParticle(Vector3 pos) {
        this(pos, 0);
    }

    public CloudParticle(Vector3 pos, int scale) {
        super(pos, ParticleType.EVAPORATION, scale);
    }
}
