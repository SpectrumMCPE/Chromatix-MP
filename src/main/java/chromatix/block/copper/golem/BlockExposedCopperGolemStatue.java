package chromatix.block.copper.golem;

import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.OxidizationLevel;
import org.jetbrains.annotations.NotNull;

/**
 * @author keksdev
 * @since 1.21.110
 */
public class BlockExposedCopperGolemStatue extends BlockCopperGolemStatue {
    public static final BlockProperties PROPERTIES = new BlockProperties(EXPOSED_COPPER_GOLEM_STATUE, CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockExposedCopperGolemStatue() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockExposedCopperGolemStatue(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Exposed Copper Golem Statue";
    }

    @Override
    public @NotNull OxidizationLevel getOxidizationLevel() {
        return OxidizationLevel.EXPOSED;
    }
}
