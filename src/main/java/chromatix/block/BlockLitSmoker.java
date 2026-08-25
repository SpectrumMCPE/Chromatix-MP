package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntitySmoker;
import chromatix.item.Item;
import chromatix.item.ItemBlock;
import org.jetbrains.annotations.NotNull;

public class BlockLitSmoker extends BlockLitFurnace {
    public static final BlockProperties PROPERTIES = new BlockProperties(LIT_SMOKER, CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockLitSmoker() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockLitSmoker(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Burning Smoker";
    }

    @Override
    @NotNull public String getBlockEntityType() {
        return BlockEntity.SMOKER;
    }

    @Override
    @NotNull public Class<? extends BlockEntitySmoker> getBlockEntityClass() {
        return BlockEntitySmoker.class;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(new BlockSmoker());
    }
}