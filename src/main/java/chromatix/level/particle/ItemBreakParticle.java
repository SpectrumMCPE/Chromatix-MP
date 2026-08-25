package chromatix.level.particle;

import chromatix.item.Item;
import chromatix.math.Vector3;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;

/**
 * @author xtypr
 * @since 2015/11/21
 */
public class ItemBreakParticle extends GenericParticle {

    public ItemBreakParticle(Vector3 pos, Item item) {
        super(pos, ParticleType.ICON_CRACK, (item.getRuntimeId() << 16 | item.getDamage()));
    }
}