package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardLightBlueStainedGlass extends Block {
     public static final BlockProperties PROPERTIES = new BlockProperties(HARD_LIGHT_BLUE_STAINED_GLASS);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

     public BlockHardLightBlueStainedGlass(BlockState blockstate) {
         super(blockstate);
     }
}