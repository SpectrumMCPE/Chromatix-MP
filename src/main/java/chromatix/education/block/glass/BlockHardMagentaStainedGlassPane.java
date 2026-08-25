package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardMagentaStainedGlassPane extends Block {
     public static final BlockProperties PROPERTIES = new BlockProperties(HARD_MAGENTA_STAINED_GLASS_PANE);

     @Override
     @NotNull
     public BlockProperties getProperties() {
        return PROPERTIES;
     }

     public BlockHardMagentaStainedGlassPane(BlockState blockstate) {
         super(blockstate);
     }
}