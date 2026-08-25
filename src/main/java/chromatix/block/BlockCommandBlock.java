package chromatix.block;

import chromatix.Player;
import chromatix.Server;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityCommandBlock;
import chromatix.item.Item;
import chromatix.level.Level;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Faceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static chromatix.block.property.CommonBlockProperties.CONDITIONAL_BIT;
import static chromatix.block.property.CommonBlockProperties.FACING_DIRECTION;

//special thanks to wode
public class BlockCommandBlock extends BlockSolid implements Faceable, BlockEntityHolder<BlockEntityCommandBlock> {
    public static final BlockProperties PROPERTIES = new BlockProperties(COMMAND_BLOCK, CONDITIONAL_BIT, FACING_DIRECTION);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockCommandBlock() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockCommandBlock(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public double getResistance() {
        return 6000000;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean isBreakable(@NotNull Vector3 vector, int layer, @Nullable BlockFace face, @Nullable Item item, @Nullable Player player) {
        return player != null && player.isCreative();
    }

    @Override
    public BlockFace getBlockFace() {
        return BlockFace.fromIndex(getPropertyValue(FACING_DIRECTION));
    }

    @Override
    public void setBlockFace(BlockFace face) {
        setPropertyValue(FACING_DIRECTION, face.getIndex());
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        if (player != null) {
            if (!player.isCreative())
                return false;
            if (Math.abs(player.getFloorX() - this.x) < 2 && Math.abs(player.getFloorZ() - this.z) < 2) {
                double y = player.y + player.getEyeHeight();
                if (y - this.y > 2) {
                    this.setBlockFace(BlockFace.UP);
                } else if (this.y - y > 0) {
                    this.setBlockFace(BlockFace.DOWN);
                } else {
                    this.setBlockFace(player.getHorizontalFacing().getOpposite());
                }
            } else {
                this.setBlockFace(player.getHorizontalFacing().getOpposite());
            }
        } else {
            this.setBlockFace(BlockFace.DOWN);
        }

        CompoundTag nbt = new CompoundTag();

        if (item.hasCustomBlockData()) {
            for (var entry : item.getCustomBlockData().getEntrySet()) {
                nbt.put(entry.getKey(), entry.getValue().copy());
            }
        }

        BlockEntityCommandBlock blockEntity = BlockEntityHolder.setBlockAndCreateEntity(this, false, true, nbt);
        return blockEntity != null;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player, BlockFace blockFace, float fx, float fy, float fz) {
        if (isNotActivate(player)) return false;

        Item itemInHand = player.getInventory().getItemInMainHand();
        if (player.isSneaking() && !(itemInHand.isTool() || itemInHand.isNull()) || !Server.getInstance().getSettings().gameplaySettings().enableCommandBlocks()) {
            return false;
        }
        BlockEntityCommandBlock tile = this.getOrCreateBlockEntity();
        tile.spawnTo(player);
        player.addWindow(tile.getInventory());
        return true;
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL || type == Level.BLOCK_UPDATE_REDSTONE) {
            BlockEntityCommandBlock tile = this.getBlockEntity();
            if (tile == null)
                return super.onUpdate(type);
            if (this.isGettingPower()) {
                if (!tile.isPowered()) {
                    tile.setPowered();
                    tile.trigger();
                }
            } else {
                tile.setPowered(false);
            }
        }
        return super.onUpdate(type);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride() {
        return Math.min(this.getOrCreateBlockEntity().getSuccessCount(), 0xf);
    }

    @Override
    @NotNull
    public Class<? extends BlockEntityCommandBlock> getBlockEntityClass() {
        return BlockEntityCommandBlock.class;
    }

    @Override
    @NotNull
    public String getBlockEntityType() {
        return BlockEntity.COMMAND_BLOCK;
    }
}
