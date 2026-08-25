package chromatix.event.scoreboard;

import chromatix.event.Cancellable;
import chromatix.event.Event;
import chromatix.event.HandlerList;
import chromatix.scoreboard.IScoreboard;
import lombok.Getter;

/**
 * Note that if the scoreboard does not exist in {@link chromatix.Server}::scoreboardManager, this event will not be called
 */
@Getter
public abstract class ScoreboardEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final IScoreboard scoreboard;

    public ScoreboardEvent(IScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public IScoreboard getScoreboard(){
        return this.scoreboard;
    }
}
