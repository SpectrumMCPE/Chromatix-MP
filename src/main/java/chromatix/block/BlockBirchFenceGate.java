package chromatix.block;

import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.IN_WALL_BIT;
import static chromatix.block.property.CommonBlockProperties.MINECRAFT_CARDINAL_DIRECTION;
import static chromatix.block.property.CommonBlockProperties.OPEN_BIT;

public class BlockBirchFenceGate extends BlockFenceGate{
    public static final BlockProperties PROPERTIES = new BlockProperties(BIRCH_FENCE_GATE,  IN_WALL_BIT, MINECRAFT_CARDINAL_DIRECTION, OPEN_BIT);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockBirchFenceGate() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockBirchFenceGate(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Birch Fence Gate";
    }
}