package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardOrangeStainedGlass extends Block {
     public static final BlockProperties PROPERTIES = new BlockProperties(HARD_ORANGE_STAINED_GLASS);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

     public BlockHardOrangeStainedGlass(BlockState blockstate) {
         super(blockstate);
     }
}