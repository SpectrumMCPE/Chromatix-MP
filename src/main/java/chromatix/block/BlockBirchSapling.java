package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.WoodType;
import org.jetbrains.annotations.NotNull;

public class BlockBirchSapling extends BlockSapling {
     public static final BlockProperties PROPERTIES = new BlockProperties(BIRCH_SAPLING, CommonBlockProperties.AGE_BIT);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

    public BlockBirchSapling() {
        super(PROPERTIES.getDefaultState());
    }

     public BlockBirchSapling(BlockState blockstate) {
         super(blockstate);
     }

    @Override
    public WoodType getWoodType() {
        return WoodType.BIRCH;
    }
}