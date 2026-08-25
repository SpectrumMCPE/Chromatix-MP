package chromatix.level;

import chromatix.GameMockExtension;
import chromatix.TestPlayer;
import chromatix.block.BlockDiamondBlock;
import chromatix.block.BlockDirt;
import chromatix.block.BlockOakWood;
import chromatix.block.BlockObserver;
import chromatix.block.BlockRedstoneWire;
import chromatix.block.property.CommonBlockProperties;
import chromatix.level.format.IChunk;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import chromatix.utils.GameLoop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static chromatix.TestUtils.gameLoop0;
import static chromatix.TestUtils.resetPlayerStatus;

@ExtendWith(GameMockExtension.class)
public class LevelTest {

    @Test
    void test_getRedstonePower(Level level) {
        level.setBlockStateAt(0, 0, 0, BlockObserver.PROPERTIES.getBlockState(
                        CommonBlockProperties.POWERED_BIT.createValue(true),
                        CommonBlockProperties.MINECRAFT_FACING_DIRECTION.createValue(BlockFace.WEST)//West is the direction of the observer's face, and the opposite side of the observer's direction can output power
                )
        );
        //For example, when a redstone is in the east of the observer
        level.setBlockStateAt(1, 0, 0, BlockRedstoneWire.PROPERTIES.getDefaultState());
        //judge if there is a redstone power source in the west of the block
        Assertions.assertEquals(15, level.getRedstonePower(new Vector3(1, 0, 0).getSide(BlockFace.WEST), BlockFace.WEST));
        Assertions.assertEquals(0, level.getRedstonePower(new Vector3(1, 0, 0).getSide(BlockFace.EAST), BlockFace.EAST));//observer cant output on east

        level.setBlockStateAt(1, 0, 0, BlockOakWood.PROPERTIES.getDefaultState());
        Assertions.assertEquals(15, level.getRedstonePower(new Vector3(1, 0, 0), BlockFace.WEST));//wood be strong power with observer
    }

}
