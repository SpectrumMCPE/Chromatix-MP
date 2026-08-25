package chromatix.block.copper.lightningrod;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

/**
 * @author keksdev
 * @since 1.21.110
 */
public class BlockWaxedExposedLightningRod extends BlockWaxedLightningRod {
    public static final BlockProperties PROPERTIES = new BlockProperties(WAXED_EXPOSED_LIGHTNING_ROD, CommonBlockProperties.FACING_DIRECTION, CommonBlockProperties.POWERED_BIT);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWaxedExposedLightningRod() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWaxedExposedLightningRod(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Waxed Exposed Lightning Rod";
    }

    @Override
    public @NotNull OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.EXPOSED;
    }
}
