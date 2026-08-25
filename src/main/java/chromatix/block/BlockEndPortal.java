package chromatix.block;

import chromatix.Player;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityEndPortal;
import chromatix.item.Item;
import chromatix.item.ItemBlock;
import chromatix.level.Level;
import chromatix.level.Position;
import chromatix.math.AxisAlignedBB;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BlockEndPortal extends BlockFlowable implements BlockEntityHolder<BlockEntityEndPortal> {

    public static final BlockProperties PROPERTIES = new BlockProperties(END_PORTAL);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockEndPortal() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockEndPortal(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "End Portal Block";
    }

    @Override
    @NotNull public Class<? extends BlockEntityEndPortal> getBlockEntityClass() {
        return BlockEntityEndPortal.class;
    }

    @Override
    @NotNull public String getBlockEntityType() {
        return BlockEntity.END_PORTAL;
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        return BlockEntityHolder.setBlockAndCreateEntity(this) != null;
    }

    @Override
    public boolean canPassThrough() {
        return false;
    }

    @Override
    public boolean isBreakable(@NotNull Vector3 vector, int layer, @Nullable BlockFace face, @Nullable Item item, @Nullable Player player) {
        return player != null && player.isCreative();
    }

    @Override
    public double getHardness() {
        return -1;
    }

    @Override
    public double getResistance() {
        return 18000000;
    }

    @Override
    public int getLightLevel() {
        return 15;
    }

    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean canBeFlowedInto() {
        return false;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(Block.get(BlockID.AIR));
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBePulled() {
        return false;
    }

    @Override
    public double getMaxY() {
        return getY() + (12.0 / 16.0);
    }

    public static void spawnObsidianPlatform(Position position) {
        Level level = position.getLevel();
        int x = position.getFloorX();
        int y = position.getFloorY();
        int z = position.getFloorZ();

        for (int blockX = x - 2; blockX <= x + 2; blockX++) {
            for (int blockZ = z - 2; blockZ <= z + 2; blockZ++) {
                level.setBlockStateAt(blockX, y - 1, blockZ, BlockObsidian.PROPERTIES.getDefaultState());
                for (int blockY = y; blockY <= y + 3; blockY++) {
                    level.setBlockStateAt(blockX, blockY, blockZ, BlockAir.STATE);
                }
            }
        }
    }
}
