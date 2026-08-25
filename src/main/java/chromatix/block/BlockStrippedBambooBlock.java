package chromatix.block;

import chromatix.Player;
import chromatix.block.property.enums.WoodType;
import chromatix.item.Item;
import chromatix.math.BlockFace;
import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.PILLAR_AXIS;


public class BlockStrippedBambooBlock extends BlockWoodStripped {
    public static final BlockProperties PROPERTIES = new BlockProperties(STRIPPED_BAMBOO_BLOCK, PILLAR_AXIS);

    @Override
    @NotNull
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockStrippedBambooBlock() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockStrippedBambooBlock(BlockState blockState) {
        super(blockState);
    }

    public String getName() {
        return "Stripped Bamboo Block";
    }

    @Override
    public WoodType getWoodType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockState getStrippedState() {
        return getBlockState();
    }

    @Override
    public boolean canBeActivated() {
        return false;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player, BlockFace blockFace, float fx, float fy, float fz) {
        return false;
    }

    @Override
    public double getHardness() {
        return 2;
    }

    @Override
    public double getResistance() {
        return 15;
    }

    @Override
    public int getBurnChance() {
        return 5;
    }

    @Override
    public int getBurnAbility() {
        return 20;
    }
}