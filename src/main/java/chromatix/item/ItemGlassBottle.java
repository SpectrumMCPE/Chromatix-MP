package chromatix.item;


import chromatix.Player;
import chromatix.block.Block;
import chromatix.block.BlockBeehive;
import chromatix.block.BlockID;
import chromatix.entity.item.EntityAreaEffectCloud;
import chromatix.level.Level;
import chromatix.level.Sound;
import chromatix.level.vibration.VibrationEvent;
import chromatix.level.vibration.VibrationType;
import chromatix.math.BlockFace;

public class ItemGlassBottle extends Item {

    public ItemGlassBottle() {
        this(0, 1);
    }

    public ItemGlassBottle(Integer meta) {
        this(meta, 1);
    }

    public ItemGlassBottle(Integer meta, int count) {
        super(GLASS_BOTTLE, meta, count, "Glass Bottle");
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, BlockFace face,
                              double fx, double fy, double fz) {
        if (player == null) {
            return false;
        }

        Item filled = null;

        var box = player.getBoundingBox().grow(1.1, 1.1, 1.1);
        var collidingEntities = level.getCollidingEntities(box);

        for (var entity : collidingEntities) {
            if (entity instanceof EntityAreaEffectCloud cloud && cloud.isDragonBreath()) {
                filled = new ItemDragonBreath();
                cloud.setRadius(cloud.getRadius() - 1, true);
                break;
            }
        }

        if (filled == null) {
            String targetId = target.getId();
            if (targetId.equals(BlockID.WATER) || targetId.equals(BlockID.FLOWING_WATER)) {
                filled = new ItemPotion();
            } else if (target instanceof BlockBeehive beehive && beehive.isFull()) {
                filled = Item.get(HONEY_BOTTLE);
                beehive.honeyCollected(player);
                level.addSound(player, Sound.BUCKET_FILL_WATER);
            }
        }

        if (filled == null) {
            return false;
        }

        if (this.count == 1) {
            player.getInventory().setItemInMainHand(filled);
        } else {
            this.count--;
            player.getInventory().setItemInMainHand(this);

            if (player.getInventory().canAddItem(filled)) {
                player.getInventory().addItem(filled);
            } else {
                level.dropItem(player.add(0, 1.3, 0), filled, player.getDirectionVector().multiply(0.4));
            }
        }

        level.getVibrationManager().callVibrationEvent(
                new VibrationEvent(
                        player,
                        target.add(0.5, 0.5, 0.5),
                        VibrationType.FLUID_PICKUP
                )
        );

        return false;
    }
}
