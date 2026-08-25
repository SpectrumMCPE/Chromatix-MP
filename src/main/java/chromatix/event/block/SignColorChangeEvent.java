package chromatix.event.block;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.utils.BlockColor;

public class SignColorChangeEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;
    private final BlockColor color;

    public SignColorChangeEvent(Block block, Player player, BlockColor color) {
        super(block);
        this.player = player;
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public BlockColor getColor() {
        return this.color;
    }
}
