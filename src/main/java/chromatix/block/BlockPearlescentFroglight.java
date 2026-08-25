package chromatix.block;

import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.PILLAR_AXIS;

public class BlockPearlescentFroglight extends BlockFroglight {

    public static final BlockProperties PROPERTIES = new BlockProperties(PEARLESCENT_FROGLIGHT, PILLAR_AXIS);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockPearlescentFroglight() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockPearlescentFroglight(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "Pearlescent Froglight";
    }
}
