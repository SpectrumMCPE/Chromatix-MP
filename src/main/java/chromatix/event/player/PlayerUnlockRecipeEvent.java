package chromatix.event.player;

import org.jetbrains.annotations.NotNull;
import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.recipe.Recipe;

/**
 * Called before a recipe is added to a player's recipe book.
 * <p>
 * Cancelling the event leaves the recipe locked, the client is not notified and the recipe
 * stays a candidate for a later unlock attempt.
 *
 * @author xRookieFight
 * @since 29/07/2026
 */
public class PlayerUnlockRecipeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Recipe recipe;

    public PlayerUnlockRecipeEvent(@NotNull Player player, @NotNull Recipe recipe) {
        this.player = player;
        this.recipe = recipe;
    }

    /**
     * @return the recipe about to be unlocked, never {@code null}
     */
    public @NotNull Recipe getRecipe() {
        return this.recipe;
    }
}
