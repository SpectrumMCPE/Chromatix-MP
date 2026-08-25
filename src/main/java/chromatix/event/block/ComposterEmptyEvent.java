package chromatix.event.block;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;
import chromatix.math.Vector3;


public class ComposterEmptyEvent extends BlockEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private Item drop;
    private Item itemUsed;
    private int newLevel;
    private Vector3 motion;


    public ComposterEmptyEvent(Block block, Player player, Item itemUsed, Item drop, int newLevel) {
        super(block);
        this.player = player;
        this.drop = drop;
        this.itemUsed = itemUsed;
        this.newLevel = Math.max(0, Math.min(newLevel, 8));
    }

    public Player getPlayer() {
        return player;
    }

    public Item getDrop() {
        return drop.clone();
    }

    public void setDrop(Item drop) {
        if (drop == null) {
            drop = Item.AIR;
        } else {
            drop = drop.clone();
        }
        this.drop = drop;
    }

    public Item getItemUsed() {
        return itemUsed;
    }

    public void setItemUsed(Item itemUsed) {
        this.itemUsed = itemUsed;
    }

    public int getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(int newLevel) {
        this.newLevel = Math.max(0, Math.min(newLevel, 8));
    }

    public Vector3 getMotion() {
        return motion;
    }

    public void setMotion(Vector3 motion) {
        this.motion = motion;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

}
