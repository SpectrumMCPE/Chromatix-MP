package chromatix.block;

import chromatix.item.Item;
import chromatix.item.ItemMangroveSign;
import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.FACING_DIRECTION;


public class BlockMangroveWallSign extends BlockWallSign {
    public static final BlockProperties PROPERTIES = new BlockProperties(MANGROVE_WALL_SIGN, FACING_DIRECTION);

    public BlockMangroveWallSign() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockMangroveWallSign(BlockState blockState) {
        super(blockState);
    }

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getWallSignId() {
        return MANGROVE_WALL_SIGN;
    }

    @Override
    public String getStandingSignId() {
        return MANGROVE_STANDING_SIGN;
    }

    @Override
    public String getName() {
        return "Mangrove Wall Sign";
    }

    @Override
    public Item toItem() {
        return new ItemMangroveSign();
    }
}
