package chromatix.block;

import chromatix.Player;
import chromatix.Server;
import chromatix.event.block.BlockSpreadEvent;
import chromatix.item.Item;
import chromatix.item.ItemBlock;
import chromatix.item.ItemTool;
import chromatix.level.Level;
import chromatix.level.Sound;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import chromatix.utils.random.NukkitRandom;
import org.jetbrains.annotations.NotNull;

/**
 * @author Pub4Game
 * @since 03.01.2016
 */
public class BlockMycelium extends BlockDirt {
    public static final BlockProperties PROPERTIES = new BlockProperties(MYCELIUM);

    @Override
    @NotNull public BlockProperties getProperties() {
        return PROPERTIES;
    }

    public BlockMycelium() {
        super(PROPERTIES.getDefaultState());
    }

    public BlockMycelium(BlockState blockState) {
        super(blockState);
    }

    @Override
    public String getName() {
        return "Mycelium";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[]{
                new ItemBlock(Block.get(BlockID.DIRT))
        };
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (getLevel().getFullLight(add(0, 1, 0)) >= BlockCrops.MINIMUM_LIGHT_LEVEL) {
                //TODO: light levels
                NukkitRandom random = new NukkitRandom();
                x = random.nextInt((int) x - 1, (int) x + 1);
                y = random.nextInt((int) y - 1, (int) y + 1);
                z = random.nextInt((int) z - 1, (int) z + 1);
                Block block = this.getLevel().getBlock(new Vector3(x, y, z));
                if (block.getId().equals(Block.DIRT)) {
                    if (block.up().isTransparent()) {
                        BlockSpreadEvent ev = new BlockSpreadEvent(block, this, Block.get(BlockID.MYCELIUM));
                        Server.getInstance().getPluginManager().callEvent(ev);
                        if (!ev.isCancelled()) {
                            this.getLevel().setBlock(block, ev.getNewState());
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(@NotNull Item item, Player player, BlockFace blockFace, float fx, float fy, float fz) {
        if (item.isShovel()) {
            if (up().isAir()) {
                item.useOn(this);
                this.getLevel().setBlock(this, Block.get(BlockID.GRASS_PATH));
                if (player != null) {
                    player.getLevel().addSound(player, Sound.USE_GRASS);
                }
                return true;
            }
        }
        return false;
    }
}
