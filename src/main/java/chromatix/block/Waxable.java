package chromatix.block;

import chromatix.Player;
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.level.Location;
import chromatix.level.Position;
import chromatix.level.particle.WaxOffParticle;
import chromatix.level.particle.WaxOnParticle;
import chromatix.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author joserobjr
 * @since 2021-06-14
 */
public interface Waxable {

    @NotNull Location getLocation();

    default boolean onActivate(@NotNull Item item, @Nullable Player player, BlockFace blockFace, float fx, float fy, float fz) {
        boolean waxed = isWaxed();
        if ((!Objects.equals(item.getId(), ItemID.HONEYCOMB) || waxed) && (!item.isAxe() || !waxed)) {
            return false;
        }

        waxed = !waxed;
        if (!setWaxed(waxed)) {
            return false;
        }

        Position location = this instanceof Block ? (Position) this : getLocation();
        if (player == null || !player.isCreative()) {
            if (waxed) {
                item.count--;
            } else {
                item.useOn(this instanceof Block? (Block) this : location.getLevelBlock());
            }
        }
        location.getValidLevel().addParticle(waxed? new WaxOnParticle(location) : new WaxOffParticle(location));
        return true;
    }

    boolean isWaxed();

    boolean setWaxed(boolean waxed);
}
