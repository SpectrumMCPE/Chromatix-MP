package chromatix.event.scoreboard;

import chromatix.event.HandlerList;
import chromatix.scoreboard.IScoreboard;

public class ScoreboardObjectiveChangeEvent extends ScoreboardEvent{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final ActionType actionType;

    public ScoreboardObjectiveChangeEvent(IScoreboard scoreboard, ActionType actionType) {
        super(scoreboard);
        this.actionType = actionType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public enum ActionType{
        ADD,
        REMOVE
    }
}
