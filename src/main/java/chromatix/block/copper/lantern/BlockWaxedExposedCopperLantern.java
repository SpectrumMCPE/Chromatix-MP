package chromatix.block.copper.lantern;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

/**
 * @author keksdev
 * @since 1.21.110
 */
public class BlockWaxedExposedCopperLantern extends BlockWaxedCopperLantern {
    public static final BlockProperties PROPERTIES = new BlockProperties(WAXED_EXPOSED_COPPER_LANTERN, CommonBlockProperties.HANGING);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWaxedExposedCopperLantern() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWaxedExposedCopperLantern(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Waxed Exposed Copper Lantern";
    }

    @Override
    public @NotNull OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.EXPOSED;
    }
}
