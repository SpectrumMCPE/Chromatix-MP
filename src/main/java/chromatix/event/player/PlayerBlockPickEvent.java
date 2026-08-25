package chromatix.event.player;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;

/**
 * @author CreeperFace
 */
public class PlayerBlockPickEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Block blockClicked;
    private Item item;

    public PlayerBlockPickEvent(Player player, Block blockClicked, Item item) {
        this.blockClicked = blockClicked;
        this.item = item;
        this.player = player;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Block getBlockClicked() {
        return blockClicked;
    }
}
