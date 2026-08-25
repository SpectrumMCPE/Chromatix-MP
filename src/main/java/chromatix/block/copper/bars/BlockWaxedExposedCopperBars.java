package chromatix.block.copper.bars;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.enums.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

/**
 * @author keksdev
 * @since 1.21.110
 */
public class BlockWaxedExposedCopperBars extends BlockWaxedCopperBars {
    public static final BlockProperties PROPERTIES = new BlockProperties(WAXED_EXPOSED_COPPER_BARS);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWaxedExposedCopperBars() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWaxedExposedCopperBars(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Waxed Exposed Copper Bars";
    }

    @Override
    public @NotNull OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.EXPOSED;
    }
}
