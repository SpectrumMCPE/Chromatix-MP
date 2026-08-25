package chromatix.block.copper.chain;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

/**
 * @author keksdev
 * @since 1.21.110
 */
public class BlockWaxedOxidizedCopperChain extends BlockWaxedCopperChain {
    public static final BlockProperties PROPERTIES = new BlockProperties(WAXED_OXIDIZED_COPPER_CHAIN, CommonBlockProperties.PILLAR_AXIS);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockWaxedOxidizedCopperChain() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockWaxedOxidizedCopperChain(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Waxed Oxidized Copper Chain";
    }

    @Override
    public @NotNull OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.OXIDIZED;
    }
}
