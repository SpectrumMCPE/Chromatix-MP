package chromatix.block.copper.bulb;

import chromatix.block.BlockID;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

public class BlockCopperBulb extends BlockCopperBulbBase {
    public static final BlockProperties PROPERTIES = new BlockProperties(BlockID.COPPER_BULB, CommonBlockProperties.LIT, CommonBlockProperties.POWERED_BIT);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockCopperBulb() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockCopperBulb(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public @NotNull OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.UNAFFECTED;
    }

    @Override
    public int getLightLevel() {
        return getLit() ? 15 : 0;
    }
}