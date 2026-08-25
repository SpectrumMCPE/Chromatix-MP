package chromatix.block;

import chromatix.block.property.CommonBlockProperties;
import chromatix.block.property.enums.WoodType;
import org.jetbrains.annotations.NotNull;

public class BlockBirchWood extends BlockWood {
     public static final BlockProperties PROPERTIES = new BlockProperties(BIRCH_WOOD, CommonBlockProperties.PILLAR_AXIS);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

     public BlockBirchWood(BlockState blockstate) {
         super(blockstate);
     }

    @Override
    public WoodType getWoodType() {
        return WoodType.BIRCH;
    }
}