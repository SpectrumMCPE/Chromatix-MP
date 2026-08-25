package chromatix.block;

import chromatix.Player;
import chromatix.item.Item;
import chromatix.math.AxisAlignedBB;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class BlockAir extends BlockTransparent {
    public static final BlockProperties PROPERTIES = new BlockProperties(AIR);
    public static final BlockState STATE = PROPERTIES.getDefaultState();

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockAir() {
        super(STATE);
    }

    public BlockAir(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "Air";
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public boolean isBreakable(@NotNull Vector3 vector, int layer, @Nullable BlockFace face, @Nullable Item item, @Nullable Player player) {
        return false;
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }

    @Override
    public boolean canBePlaced() {
        return false;
    }

    @Override
    public boolean canBeReplaced() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isSolid(BlockFace side) {
        return false;
    }

    @Override
    public double getHardness() {
        return 0;
    }

    @Override
    public double getResistance() {
        return 0;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    protected AxisAlignedBB recalculateCollisionBoundingBox() {
        return null;
    }
}
