package chromatix.event.player;

import chromatix.Player;
import chromatix.block.Block;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;
import chromatix.level.Position;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import org.jetbrains.annotations.Nullable;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class PlayerInteractEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected @Nullable final Block blockTouched;
    protected @Nullable final BlockFace blockFace;
    protected @Nullable final Item item;
    protected final Vector3 touchVector;
    protected final Action action;

    public PlayerInteractEvent(Player player, Item item, Vector3 block, BlockFace face) {
        this(player, item, block, face, Action.RIGHT_CLICK_BLOCK);
    }

    public PlayerInteractEvent(Player player, @Nullable Item item, Vector3 block, @Nullable BlockFace face, Action action) {
        if (block instanceof Block block1) {
            this.blockTouched = block1;
            this.touchVector = new Vector3(0, 0, 0);
        } else {
            this.touchVector = block;
            this.blockTouched = Block.get(Block.AIR, new Position(0, 0, 0, player.level));
        }

        this.player = player;
        this.item = item;
        this.blockFace = face;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public @Nullable Item getItem() {
        return item;
    }

    public @Nullable Block getBlock() {
        return blockTouched;
    }

    public Vector3 getTouchVector() {
        return touchVector;
    }

    public @Nullable BlockFace getFace() {
        return blockFace;
    }

    public enum Action {
        LEFT_CLICK_BLOCK,
        RIGHT_CLICK_BLOCK,
        RIGHT_HOLD_BLOCK,
        LEFT_CLICK_AIR,
        RIGHT_CLICK_AIR,
        PHYSICAL
    }
}
