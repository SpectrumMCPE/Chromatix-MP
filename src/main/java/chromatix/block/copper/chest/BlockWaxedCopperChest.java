package chromatix.block.copper.chest;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import org.jetbrains.annotations.NotNull;

/**
 * @author keksdev
 * @since 1.21.110
 */
public class BlockWaxedCopperChest extends BlockCopperChest {
    public static final BlockProperties PROPERTIES = new BlockProperties(WAXED_COPPER_CHEST, CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWaxedCopperChest() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWaxedCopperChest(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Waxed Copper Chest";
    }

    @Override
    public boolean isWaxed() {
        return true;
    }
}
