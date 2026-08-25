package chromatix.block;

import chromatix.Player;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityEnchantTable;
import chromatix.item.Item;
import chromatix.item.ItemTool;
import chromatix.math.BlockFace;
import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.StringTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/**
 * @author CreeperFace
 * @since 2015/11/22
 */

public class BlockEnchantingTable extends BlockTransparent implements BlockEntityHolder<BlockEntityEnchantTable> {

    public static final BlockProperties PROPERTIES = new BlockProperties(ENCHANTING_TABLE);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockEnchantingTable() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockEnchantingTable(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Enchanting Table";
    }

    @Override
    @NotNull
    public String getBlockEntityType() {
        return BlockEntity.ENCHANT_TABLE;
    }

    @Override
    @NotNull
    public Class<? extends BlockEntityEnchantTable> getBlockEntityClass() {
        return BlockEntityEnchantTable.class;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public double getResistance() {
        return 6000;
    }

    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @Override
    public int getLightLevel() {
        return 7;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public double getMaxY() {
        return getY() + 12 / 16.0;
    }

    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        CompoundTag nbt = new CompoundTag();

        if (item.hasCustomName()) {
            nbt.putString("CustomName", item.getCustomName());
        }

        if (item.hasCustomBlockData()) {
            for (var entry : item.getCustomBlockData().getEntrySet()) {
                nbt.put(entry.getKey(), entry.getValue().copy());
            }
        }

        return BlockEntityHolder.setBlockAndCreateEntity(this, false, true, nbt) != null;
    }

    @Override
    public boolean onActivate(@NotNull Item item, @Nullable Player player, BlockFace blockFace, float fx, float fy, float fz) {
        if (player == null) {
            return true;
        }
        Item itemInHand = player.getInventory().getItemInMainHand();
        if (player.isSneaking() && !(itemInHand.isTool() || itemInHand.isNull())) {
            return false;
        }

        BlockEntityEnchantTable enchantTable = getOrCreateBlockEntity();
        if (enchantTable.getNbt().contains("Lock") && enchantTable.getNbt().get("Lock") instanceof StringTag
                && !enchantTable.getNbt().getString("Lock").equals(item.getCustomName())) {
            return false;
        }

        player.addWindow(enchantTable.getInventory());

        return true;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean isSolid(BlockFace side) {
        return false;
    }
}
