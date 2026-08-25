package chromatix.item;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.entity.EntityID;
import chromatix.entity.passive.EntitySulfurCube;
import chromatix.event.player.PlayerBucketEmptyEvent;
import chromatix.level.Level;
import chromatix.level.Position;
import chromatix.level.Sound;
import chromatix.level.vibration.VibrationEvent;
import chromatix.level.vibration.VibrationType;
import chromatix.math.BlockFace;

public class ItemSulfurCubeBucket extends ItemBucket {
    public ItemSulfurCubeBucket() {
        super(SULFUR_CUBE_BUCKET);
    }

    @Override
    public void setDamage(int meta) {

    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face,
                              double fx, double fy, double fz) {
        if (player.isAdventure()) {
            return false;
        }
        if (!player.isItemCoolDownEnd(BUCKET)) {
            return false;
        }

        Block placement = block.canBeReplaced() ? block : target.getSide(face);
        if (!placement.canBeReplaced()) {
            return false;
        }

        PlayerBucketEmptyEvent event = new PlayerBucketEmptyEvent(
                player, placement, face, target, this, Item.get(BUCKET, 0, 1));
        player.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            player.getInventory().sendContents(player);
            return false;
        }

        if (!spawnBucketEntity(placement.add(0.5, 0, 0.5))) {
            player.getInventory().sendContents(player);
            return false;
        }

        player.setItemCoolDown(5, BUCKET);
        level.addSound(placement, Sound.BUCKET_EMPTY_FISH);
        level.getVibrationManager().callVibrationEvent(
                new VibrationEvent(player, placement.add(0.5, 0.5, 0.5), VibrationType.ENTITY_PLACE));
        updateBucketItem(player, event);
        return true;
    }

    @Override
    public boolean spawnBucketEntity(Position spawnPos) {
        Entity entity = Entity.createEntity(EntityID.SULFUR_CUBE, spawnPos);
        if (!(entity instanceof EntitySulfurCube cube)) {
            return false;
        }

        cube.readBucketTag(this.getNbt());
        cube.spawnToAll();
        return true;
    }

    /**
     * Nothing here can boil away, so the nether restriction the fluid buckets carry does not apply.
     */
    @Override
    protected boolean canBeUsedOnDimension(int dimension) {
        return true;
    }
}
