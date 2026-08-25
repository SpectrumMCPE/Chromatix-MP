package chromatix.block;

import chromatix.block.property.enums.WoodType;
import chromatix.item.Item;
import org.jetbrains.annotations.NotNull;

import static chromatix.block.property.CommonBlockProperties.PERSISTENT_BIT;
import static chromatix.block.property.CommonBlockProperties.UPDATE_BIT;

public class BlockAzaleaLeaves extends BlockLeaves {

    public static final BlockProperties PROPERTIES = new BlockProperties(AZALEA_LEAVES, PERSISTENT_BIT, UPDATE_BIT);

    public BlockAzaleaLeaves() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockAzaleaLeaves(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "Azalea Leaves";
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean canHarvest(Item item) {
        return item.isShears();
    }

    @Override
    @NotNull public  BlockProperties getProperties() {
        return PROPERTIES;
    }

    /*the wood type is set to OAK only so the drop probabilities are correct, it does not mean this is actually oak*/
    @Override
    public WoodType getType() {
        return WoodType.OAK;
    }

}
