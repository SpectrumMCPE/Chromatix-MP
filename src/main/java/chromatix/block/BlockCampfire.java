package chromatix.block;

import chromatix.Player;
import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.MinecraftCardinalDirection;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityCampfire;
import chromatix.entity.Entity;
import chromatix.entity.effect.EffectType;
import chromatix.entity.item.EntitySplashPotion;
import chromatix.entity.projectile.EntityArrow;
import chromatix.entity.projectile.EntityProjectile;
import chromatix.entity.projectile.EntitySmallFireball;
import chromatix.event.entity.EntityDamageByBlockEvent;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.inventory.CampfireInventory;
import chromatix.inventory.ContainerInventory;
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.item.ItemTool;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.Level;
import chromatix.level.Position;
import chromatix.level.Sound;
import chromatix.math.AxisAlignedBB;
import chromatix.math.BlockFace;
import chromatix.math.SimpleAxisAlignedBB;
import chromatix.math.Vector3;
import chromatix.nbt.tag.CompoundTag;
import chromatix.recipe.CampfireRecipe;
import chromatix.utils.Faceable;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

import static chromatix.block.property.CommonBlockProperties.EXTINGUISHED;


@Slf4j
public class BlockCampfire extends BlockTransparent implements Faceable, BlockEntityHolder<BlockEntityCampfire> {
    public static final BlockProperties PROPERTIES = new BlockProperties(CAMPFIRE, EXTINGUISHED, CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION);

    public BlockCampfire() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockCampfire(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    @NotNull
    public String getBlockEntityType() {
        return BlockEntity.CAMPFIRE;
    }

    @Override
    @NotNull
    public Class<? extends BlockEntityCampfire> getBlockEntityClass() {
        return BlockEntityCampfire.class;
    }

    @Override
    public int getLightLevel() {
        return isExtinguished() ? 0 : 15;
    }

    @Override
    public double getResistance() {
        return 2;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_AXE;
    }

    @Override
    public boolean canHarvestWithHand() {
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{Item.get(ItemID.CHARCOAL, 0, 2)};
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        if (down().getProperties() == PROPERTIES) {
            return false;
        }

        final Block layer0 = level.getBlock(this, 0);
        final Block layer1 = level.getBlock(this, 1);

        setBlockFace(player != null ? player.getDirection().getOpposite() : null);
        boolean defaultLayerCheck = (block instanceof BlockFlowingWater w && w.isSourceOrFlowingDown()) || block instanceof BlockFrostedIce;
        boolean layer1Check = (layer1 instanceof BlockFlowingWater w && w.isSourceOrFlowingDown()) || layer1 instanceof BlockFrostedIce;
        if (defaultLayerCheck || layer1Check) {
            setExtinguished(true);
            this.level.addSound(this, Sound.RANDOM_FIZZ, 0.5f, 2.2f);
            this.level.setBlock(this, 1, defaultLayerCheck ? block : layer1, false, false);
        } else {
            this.level.setBlock(this, 1, Block.get(BlockID.AIR), false, false);
        }

        this.level.setBlock(block, this, true, true);
        try {
            CompoundTag nbt = new CompoundTag();

            if (item.hasCustomBlockData()) {
                for (var entry : item.getCustomBlockData().getEntrySet()) {
                    nbt.put(entry.getKey(), entry.getValue().copy());
                }
            }

            createBlockEntity(nbt);
        } catch (Exception e) {
            log.warn("Failed to create the block entity {} at {}", getBlockEntityType(), getLocation(), e);
            level.setBlock(layer0, 0, layer0, true);
            level.setBlock(layer1, 0, layer1, true);
            return false;
        }

        this.level.updateAround(this);
        return true;
    }

    @Override
    public boolean hasEntityCollision() {
        return true;
    }

    @Override
    public void onEntityCollide(Entity entity) {
        if (isExtinguished()) {
            if (entity.isOnFire()) {
                setExtinguished(false);
                level.setBlock(this, this, true);
            }
            return;
        }

        if (entity.hasEffect(EffectType.FIRE_RESISTANCE)
                || entity instanceof EntityProjectile
                || !entity.attack(getDamageEvent(entity))
                || !entity.isAlive()) {
            return;
        }

        entity.attack(getDamageEvent(entity));
    }

    protected EntityDamageEvent getDamageEvent(Entity entity) {
        return new EntityDamageByBlockEvent(this, entity, EntityDamageEvent.DamageCause.FIRE, 1);
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    protected CampfireRecipe findRecipe(Item item) {
        return this.level.getServer().getRecipeRegistry().findCampfireRecipe(item);
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_NORMAL) {
            if (!isExtinguished()) {
                Block layer1 = getLevelBlockAtLayer(1);
                if (layer1 instanceof BlockFlowingWater || layer1 instanceof BlockFrostedIce) {
                    setExtinguished(true);
                    this.level.setBlock(this, this, true, true);
                    this.level.addSound(this, Sound.RANDOM_FIZZ, 0.5f, 2.2f);
                }
            }
            return type;
        }
        return 0;
    }

    @Override
    public boolean onActivate(@NotNull Item item, @Nullable Player player, BlockFace blockFace, float fx, float fy, float fz) {
        if (item.isNull()) {
            return false;
        }

        BlockEntityCampfire campfire = getOrCreateBlockEntity();

        boolean itemUsed = false;
        if (!isExtinguished()) {
            if (item.isShovel()) {
                setExtinguished(true);
                this.level.setBlock(this, this, true, true);
                this.level.addSound(this, Sound.RANDOM_FIZZ, 0.5f, 2.2f);
                itemUsed = true;
            }
        } else if (Objects.equals(item.getId(), ItemID.FLINT_AND_STEEL) || item.getEnchantment(Enchantment.ID_FIRE_ASPECT) != null) {
            item.useOn(this);
            setExtinguished(false);
            this.level.setBlock(this, this, true, true);
            campfire.scheduleUpdate();
            this.level.addSound(this, Sound.FIRE_IGNITE);
            itemUsed = true;
        }

        Item cloned = item.clone();
        cloned.setCount(1);
        CampfireInventory inventory = campfire.getInventory();
        if (inventory.canAddItem(cloned)) {
            CampfireRecipe recipe = findRecipe(cloned);
            if (recipe != null) {
                inventory.addItem(cloned);
                item.setCount(item.getCount() - 1);
                return true;
            }
        }

        return itemUsed;
    }

    @Override
    public boolean onProjectileHit(@NotNull Entity projectile, @NotNull Position position, @NotNull Vector3 motion) {
        if ((projectile instanceof EntitySmallFireball || (projectile.isOnFire() && projectile instanceof EntityArrow)) && isExtinguished()) {
            setExtinguished(false);
            level.setBlock(this, this, true);
            return true;
        } else if (projectile instanceof EntitySplashPotion && !isExtinguished()
                && ((EntitySplashPotion) projectile).potionId == 0) {
            setExtinguished(true);
            level.setBlock(this, this, true);
            return true;
        }
        return false;
    }

    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @Override
    public double getMaxY() {
        return y + 0.4371948;
    }

    @Override
    protected AxisAlignedBB recalculateCollisionBoundingBox() {
        return new SimpleAxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
    }

    public boolean isExtinguished() {
        return getPropertyValue(EXTINGUISHED);
    }

    public void setExtinguished(boolean extinguished) {
        setPropertyValue(EXTINGUISHED, extinguished);
    }

    @Override
    public BlockFace getBlockFace() {
        return BlockFace.fromHorizontalIndex(getPropertyValue(CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION).ordinal());
    }

    @Override
    public void setBlockFace(BlockFace face) {
        setPropertyValue(CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION, MinecraftCardinalDirection.values()[face.getOpposite().getHorizontalIndex()]);
    }

    @Override
    public String getName() {
        return "Campfire";
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride() {
        BlockEntityCampfire blockEntity = getBlockEntity();

        if (blockEntity != null) {
            return ContainerInventory.calculateRedstone(blockEntity.getInventory());
        }

        return super.getComparatorInputOverride();
    }

    @Override
    public boolean breaksWhenMoved() {
        return true;
    }

    @Override
    public boolean canBePulled() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }
}
