package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.WoodType;
import org.jetbrains.annotations.NotNull;

public class BlockStrippedSpruceLog extends BlockWoodStripped {
    public static final BlockProperties PROPERTIES = new BlockProperties(STRIPPED_SPRUCE_LOG, CommonBlockProperties.PILLAR_AXIS);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockStrippedSpruceLog() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockStrippedSpruceLog(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public WoodType getWoodType() {
        return WoodType.SPRUCE;
    }
}