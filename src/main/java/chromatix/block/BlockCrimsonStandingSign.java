package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.item.Item;
import chromatix.item.ItemCrimsonSign;
import org.jetbrains.annotations.NotNull;

public class BlockCrimsonStandingSign extends BlockStandingSign {
    public static final BlockProperties PROPERTIES = new BlockProperties(CRIMSON_STANDING_SIGN, CommonBlockProperties.GROUND_SIGN_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockCrimsonStandingSign() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockCrimsonStandingSign(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getWallSignId() {
        return BlockCrimsonWallSign.PROPERTIES.getIdentifier();
    }

    @Override
    public Item toItem() {
        return new ItemCrimsonSign();
    }
}