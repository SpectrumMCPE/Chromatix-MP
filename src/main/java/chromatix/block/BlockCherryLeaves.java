package chromatix.block;

import chromatix.block.property.enums.WoodType;
import chromatix.item.Item;
import chromatix.utils.BlockColor;
import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.PERSISTENT_BIT;
import static chromatix.block.property.CommonBlockProperties.UPDATE_BIT;


public class BlockCherryLeaves extends BlockLeaves {

    public static final BlockProperties PROPERTIES = new BlockProperties(CHERRY_LEAVES,PERSISTENT_BIT, UPDATE_BIT);

    public BlockCherryLeaves() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockCherryLeaves(BlockState blockState) {
        super(blockState);
    }


    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getName() {
        return "Cherry Leaves";
    }

    /*the wood type is set to BIRCH only so the drop probabilities are correct, it does not mean this is actually birch*/

    @Override
    public WoodType getType() {
        return WoodType.CHERRY;
    }

    @Override
    public Item toSapling() {
        return Item.get(CHERRY_SAPLING);
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.PINK_BLOCK_COLOR;
    }
}
