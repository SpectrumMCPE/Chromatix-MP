package chromatix.block;

import chromatix.item.Item;
import chromatix.item.ItemBlock;
import chromatix.level.Level;
import chromatix.math.BlockFace;

import static chromatix.block.property.CommonBlockProperties.CORAL_DIRECTION;

public abstract class BlockDeadCoralWallFan extends BlockCoralFanDead {

    public BlockDeadCoralWallFan(BlockState blockstate) {
        super(blockstate);
    }

    @Override
    public String getName() {
        return "Dead " + super.getName() + " Wall Fan";
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM) {
            return type;
        } else {
            return super.onUpdate(type);
        }
    }

    @Override
    public BlockFace getBlockFace() {
        int face = getPropertyValue(CORAL_DIRECTION);
        return switch (face) {
            case 0 -> BlockFace.WEST;
            case 1 -> BlockFace.EAST;
            case 2 -> BlockFace.NORTH;
            default -> BlockFace.SOUTH;
        };
    }

    @Override
    public BlockFace getRootsFace() {
        return getBlockFace().getOpposite();
    }

    @Override
    public Item toItem() {
        return new ItemBlock(isDead() ? getDeadCoralFan() : this);
    }
}