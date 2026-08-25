package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityBlastFurnace;
import chromatix.item.Item;
import chromatix.item.ItemBlock;
import org.jetbrains.annotations.NotNull;

public class BlockLitBlastFurnace extends BlockLitFurnace {
    public static final BlockProperties PROPERTIES = new BlockProperties(LIT_BLAST_FURNACE, CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockLitBlastFurnace() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockLitBlastFurnace(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Burning Blast Furnace";
    }

    @Override
    @NotNull public String getBlockEntityType() {
        return BlockEntity.BLAST_FURNACE;
    }

    @Override
    @NotNull public Class<? extends BlockEntityBlastFurnace> getBlockEntityClass() {
        return BlockEntityBlastFurnace.class;
    }

    @Override
    public Item toItem() {
        return new ItemBlock(new BlockBlastFurnace());
    }
}