package chromatix.block;

import chromatix.entity.Entity;
import chromatix.entity.projectile.EntitySmallFireball;
import chromatix.item.Item;
import chromatix.level.Position;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import org.jetbrains.annotations.NotNull;


public class BlockPowderSnow extends BlockTransparent {
    public static final BlockProperties PROPERTIES = new BlockProperties(POWDER_SNOW);

    public BlockPowderSnow() {
        super(PROPERTIES.getDefaultState());
    }

    public BlockPowderSnow(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "Powder Snow";
    }

    @Override
    public double getHardness() {
        return 0.25;
    }

    @Override
    public double getResistance() {
        return 0.1;
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
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public Item[] getDrops(Item item) {
        return Item.EMPTY_ARRAY;
    }

    @Override
    public boolean onProjectileHit(@NotNull Entity projectile, @NotNull Position position, @NotNull Vector3 motion) {
        if (projectile instanceof EntitySmallFireball) {
            this.getLevel().useBreakOn(this);
            return true;
        }
        return false;
    }
}
