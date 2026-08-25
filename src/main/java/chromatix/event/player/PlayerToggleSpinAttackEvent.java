package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

/**
 * @author GoodLucky777
 */
public class PlayerToggleSpinAttackEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final boolean isSpinAttacking;

    public PlayerToggleSpinAttackEvent(Player player, boolean isSpinAttacking) {
        this.player = player;
        this.isSpinAttacking = isSpinAttacking;
    }

    public boolean isSpinAttacking() {
        return this.isSpinAttacking;
    }
}
