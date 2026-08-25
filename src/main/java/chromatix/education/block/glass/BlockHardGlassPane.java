package chromatix.education.block.glass;

import chromatix.block.Block;
import chromatix.block.BlockProperties;
import chromatix.block.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockHardGlassPane extends Block {
    public static final BlockProperties PROPERTIES = new BlockProperties(HARD_GLASS_PANE);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockHardGlassPane() {
        this(PROPERTIES.getDefaultState());
    }

    public BlockHardGlassPane(BlockState blockstate) {
        super(blockstate);
    }
}