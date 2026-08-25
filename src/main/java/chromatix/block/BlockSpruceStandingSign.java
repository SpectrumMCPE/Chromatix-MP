package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.item.Item;
import chromatix.item.ItemSpruceSign;
import org.jetbrains.annotations.NotNull;

public class BlockSpruceStandingSign extends BlockStandingSign {
    public static final BlockProperties PROPERTIES = new BlockProperties(SPRUCE_STANDING_SIGN, CommonBlockProperties.GROUND_SIGN_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockSpruceStandingSign() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockSpruceStandingSign(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getWallSignId() {
        return BlockSpruceWallSign.PROPERTIES.getIdentifier();
    }

    @Override
    public Item toItem() {
        return new ItemSpruceSign();
    }
}