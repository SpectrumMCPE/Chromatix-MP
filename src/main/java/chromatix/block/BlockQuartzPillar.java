package chromatix.block;

import chromatix.Player;
import chromatix.block.property.CommonBlockProperties;
import chromatix.item.Item;
import chromatix.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.PILLAR_AXIS;

public class BlockQuartzPillar extends BlockQuartzBlock {
    public static final BlockProperties PROPERTIES = new BlockProperties(QUARTZ_PILLAR, CommonBlockProperties.PILLAR_AXIS);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockQuartzPillar() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockQuartzPillar(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public boolean place(@NotNull Item item, @NotNull Block block, @NotNull Block target, @NotNull BlockFace face, double fx, double fy, double fz, Player player) {
        this.setPillarAxis(face.getAxis());
        this.getLevel().setBlock(block, this, true, true);
        return true;
    }

    public BlockFace.Axis getPillarAxis() {
        return getPropertyValue(PILLAR_AXIS);
    }

    public void setPillarAxis(BlockFace.Axis axis) {
        setPropertyValue(PILLAR_AXIS, axis);
    }
}
