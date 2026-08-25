package chromatix.item;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.block.BlockFire;
import chromatix.block.BlockID;
import chromatix.event.block.BlockIgniteEvent;
import chromatix.level.Level;
import chromatix.level.Sound;
import chromatix.level.vibration.VibrationEvent;
import chromatix.level.vibration.VibrationType;
import chromatix.math.BlockFace;

import java.util.concurrent.ThreadLocalRandom;

public class ItemFlintAndSteel extends ItemTool {

    public ItemFlintAndSteel() {
        this(0, 1);
    }

    public ItemFlintAndSteel(Integer meta) {
        this(meta, 1);
    }

    public ItemFlintAndSteel(Integer meta, int count) {
        super(FLINT_AND_STEEL, meta, count, "Flint and Steel");
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

        if (block.isAir() && target.getBurnChance() != -1 && (target.isSolid() || target.getBurnChance() > 0)) {
            if (target.getId().equals(BlockID.OBSIDIAN)) {
                if (level.getDimension() != Level.DIMENSION_THE_END) {
                    if (level.createPortal(block)) {
                        damageItem(player, block);
                        return true;
                    }
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
                    level.scheduleUpdate(fire, fire.tickRate() + ThreadLocalRandom.current().nextInt(10));
                }
                damageItem(player, block);
                return true;
            }

            damageItem(player, block);
            return true;
        }
        damageItem(player, block);
        return false;
    }

    private void damageItem(Player player, Block block) {
        if (!player.isCreative() && useOn(block)) {
            if (this.getDamage() >= this.getMaxDurability()) {
                this.count = 0;
                player.getInventory().setItemInMainHand(Item.AIR);
            } else {
                player.getInventory().setItemInMainHand(this);
            }
        }
        block.getLevel().addSound(block, Sound.FIRE_IGNITE);
    }

    @Override
    public int getMaxDurability() {
        return ItemTool.DURABILITY_FLINT_STEEL;
    }

    @Override
    public boolean useOn(Block block) {
        //todo: initiator should be an entity who use it but not null
        block.getLevel().getVibrationManager().callVibrationEvent(new VibrationEvent(null, block.add(0.5, 0.5, 0.5), VibrationType.BLOCK_PLACE));
        return super.useOn(block);
    }
}
