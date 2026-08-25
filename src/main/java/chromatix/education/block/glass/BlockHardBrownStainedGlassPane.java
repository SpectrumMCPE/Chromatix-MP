package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardBrownStainedGlassPane extends Block {
     public static final BlockProperties PROPERTIES = new BlockProperties(HARD_BROWN_STAINED_GLASS_PANE);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

     public BlockHardBrownStainedGlassPane(BlockState blockstate) {
         super(blockstate);
     }
}