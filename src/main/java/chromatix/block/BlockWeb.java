package chromatix.block;

import chromatix.entity.Entity;
import chromatix.item.Item;
import chromatix.item.ItemString;
import chromatix.item.ItemTool;
import org.jetbrains.annotations.NotNull;

public class BlockWeb extends BlockFlowable {
    public static final BlockProperties PROPERTIES = new BlockProperties(WEB);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWeb() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWeb(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public double getHardness() {
        return 4;
    }

    @Override
    public double getResistance() {
        return 20;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SWORD;
    }

    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @Override
    public void onEntityCollide(Entity entity) {
        entity.resetFallDistance();
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isShears()) {
            return new Item[]{
                    this.toItem()
            };
        } else if (item.isSword()) {
            return new Item[]{
                    new ItemString()
            };
        } else {
            return Item.EMPTY_ARRAY;
        }
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean diffusesSkyLight() {
        return true;
    }
}