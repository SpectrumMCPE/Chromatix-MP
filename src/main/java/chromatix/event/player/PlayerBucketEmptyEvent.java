package chromatix.event.player;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.HandlerList;
import chromatix.item.Item;
import chromatix.math.BlockFace;

public class PlayerBucketEmptyEvent extends PlayerBucketEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerBucketEmptyEvent(Player who, Block blockClicked, BlockFace blockFace, Block liquid, Item bucket, Item itemInHand) {
        super(who, blockClicked, blockFace, liquid, bucket, itemInHand);
    }

}
