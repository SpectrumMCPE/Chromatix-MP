package chromatix.block;

import chromatix.Player;
import chromatix.item.Item;
import chromatix.level.Level;
import chromatix.math.BlockFace;
import chromatix.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public abstract class BlockHanging extends BlockFlowable {

    public BlockHanging(BlockState blockState) {
        super(blockState);
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL && !isSupportValid()) {
            level.useBreakOn(this);
            return type;
        }
        return 0;
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        return isSupportValid() && super.place(item, block, target, face, fx, fy, fz, player);
    }

    protected boolean isSupportValid() {
        return down().hasTag(BlockTags.DIRT) || switch (down().getId()) {
            case WARPED_NYLIUM, CRIMSON_NYLIUM, SOUL_SOIL -> true;
            default -> false;
        };
    }

    @Override
    public int getBurnChance() {
        return 5;
    }
}
