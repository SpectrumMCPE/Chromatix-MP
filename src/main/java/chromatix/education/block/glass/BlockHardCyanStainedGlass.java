package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardCyanStainedGlass extends Block {
     public static final BlockProperties PROPERTIES = new BlockProperties(HARD_CYAN_STAINED_GLASS);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

     public BlockHardCyanStainedGlass(BlockState blockstate) {
         super(blockstate);
     }
}