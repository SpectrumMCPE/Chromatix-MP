package chromatix.item;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.block.BlockFire;
import chromatix.block.BlockID;
import chromatix.event.block.BlockIgniteEvent;
import chromatix.level.Level;
import chromatix.math.BlockFace;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author PetteriM1
 */
public class ItemFireCharge extends Item {

    public ItemFireCharge() {
        this(0, 1);
    }

    public ItemFireCharge(Integer meta) {
        this(meta, 1);
    }

    public ItemFireCharge(Integer meta, int count) {
        super(FIRE_CHARGE, 0, count, "Fire Charge");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face, double fx, double fy, double fz) {
        if (player.isAdventure()) {
            return false;
        }

        if (block.getId().equals(BlockID.AIR) && target.getBurnChance() != -1 && (target.isSolid() || target.getBurnChance() > 0)) {
            if (target.getId().equals(BlockID.OBSIDIAN)) {
                if (level.createPortal(block)) {
                    return true;
                }
            }

            BlockFire fire = (BlockFire) Block.get(BlockID.FIRE);
            fire.x = block.x;
            fire.y = block.y;
            fire.z = block.z;
            fire.level = level;

            if (fire.isBlockTopFacingSurfaceSolid(fire.down()) || fire.canNeighborBurn()) {
                BlockIgniteEvent e = new BlockIgniteEvent(block, null, player, BlockIgniteEvent.BlockIgniteCause.FLINT_AND_STEEL);
                block.getLevel().getServer().getPluginManager().callEvent(e);

                if (!e.isCancelled()) {
                    level.setBlock(fire, fire, true);
                    level.addLevelEvent(block, LevelEvent.SOUND_GHAST_FIREBALL, 78642);
                    level.scheduleUpdate(fire, fire.tickRate() + ThreadLocalRandom.current().nextInt(10));
                }
                if (player.isSurvival()) {
                    Item item = player.getInventory().getItemInMainHand();
                    item.setCount(item.getCount() - 1);
                    player.getInventory().setItemInMainHand(item);
                }
                return true;
            }
        }
        return false;
    }
}
