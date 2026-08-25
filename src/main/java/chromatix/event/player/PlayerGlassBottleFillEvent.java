package chromatix.event.player;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.item.Item;

public class PlayerGlassBottleFillEvent extends PlayerEvent implements Cancellable {
    protected final Item item;
    protected final Block target;

    public PlayerGlassBottleFillEvent(Player player, Block target, Item item) {
        this.player = player;
        this.target = target;
        this.item = item.clone();
    }

    public Item getItem() {
        return item;
    }

    public Block getBlock() {
        return target;
    }
}
