package chromatix.education.block;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import chromatix.block.property.CommonBlockProperties;
import chromatix.item.Item;
import chromatix.math.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static chromatix.block.property.CommonBlockProperties.DIRECTION;

public class BlockElementConstructor extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties(ELEMENT_CONSTRUCTOR, CommonBlockProperties.DIRECTION);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockElementConstructor() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockElementConstructor(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, @Nullable Player player) {
        setBlockFace(player != null ? player.getDirection() : BlockFace.SOUTH);
        return this.level.setBlock(block, this, true, true);
    }

    public void setBlockFace(BlockFace face) {
        setPropertyValue(DIRECTION, face.getHorizontalIndex());
    }
}