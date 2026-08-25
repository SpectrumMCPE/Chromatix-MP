package chromatix.utils;

import chromatix.math.BlockFace;

public interface Faceable {

    BlockFace getBlockFace();


    default void setBlockFace(BlockFace face) {
        // Does nothing by default
    }
}
