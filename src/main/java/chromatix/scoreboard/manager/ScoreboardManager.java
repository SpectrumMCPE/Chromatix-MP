package chromatix.scoreboard.manager;


import chromatix.Player;
import chromatix.Server;
import chromatix.command.data.CommandEnum;
import chromatix.entity.EntityLiving;
import chromatix.event.scoreboard.ScoreboardObjectiveChangeEvent;
import chromatix.scoreboard.data.DisplaySlot;
import chromatix.scoreboard.displayer.IScoreboardViewer;
import chromatix.scoreboard.IScoreboard;
import chromatix.scoreboard.scorer.EntityScorer;
import chromatix.scoreboard.scorer.PlayerScorer;
import chromatix.scoreboard.storage.IScoreboardStorage;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.data.command.SoftEnumUpdateType;
import org.jetbrains.annotations.Nullable;

import java.util.*;


@Getter
public class ScoreboardManager implements IScoreboardManager{

    protected Map<String, IScoreboard> scoreboards = new HashMap<>();
    protected Map<DisplaySlot, IScoreboard> display = new HashMap<>();
    protected Set<IScoreboardViewer> viewers = new HashSet<>();
    protected IScoreboardStorage storage;

    public ScoreboardManager(IScoreboardStorage storage) {
        this.storage = storage;
        read();
    }

    @Override
    public boolean addScoreboard(IScoreboard scoreboard) {
        var event = new ScoreboardObjectiveChangeEvent(scoreboard, ScoreboardObjectiveChangeEvent.ActionType.ADD);
        Server.getInstance().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        scoreboards.put(scoreboard.getObjectiveName(), scoreboard);
        CommandEnum.SCOREBOARD_OBJECTIVES.updateSoftEnum(SoftEnumUpdateType.ADD, scoreboard.getObjectiveName());
        return true;
    }

    @Override
    public boolean removeScoreboard(IScoreboard scoreboard) {
        return removeScoreboard(scoreboard.getObjectiveName());
    }

    @Override
    public boolean removeScoreboard(String objectiveName) {
        var removed = scoreboards.get(objectiveName);
        if (removed == null) return false;
        var event = new ScoreboardObjectiveChangeEvent(removed, ScoreboardObjectiveChangeEvent.ActionType.REMOVE);
        Server.getInstance().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
       scoreboards.remove(objectiveName);
       CommandEnum.SCOREBOARD_OBJECTIVES.updateSoftEnum(SoftEnumUpdateType.REMOVE, objectiveName);
       viewers.forEach(viewer -> viewer.removeScoreboard(removed));
       display.forEach((slot, scoreboard) -> {
           if (scoreboard != null && scoreboard.getObjectiveName().equals(objectiveName)) {
                display.put(slot, null);
           }
       });
       return true;
    }

    @Override
    public IScoreboard getScoreboard(String objectiveName) {
        return scoreboards.get(objectiveName);
    }

    @Override
    public boolean containScoreboard(IScoreboard scoreboard) {
        return scoreboards.containsKey(scoreboard.getObjectiveName());
    }

    @Override
    public boolean containScoreboard(String name) {
        return scoreboards.containsKey(name);
    }

    @Override
    public IScoreboard getDisplaySlot(DisplaySlot slot) {
        return display.get(slot);
    }

    @Override
    public void setDisplay(DisplaySlot slot, @Nullable IScoreboard scoreboard) {
        var old = display.put(slot, scoreboard);
        if (old != null) this.viewers.forEach(viewer -> old.removeViewer(viewer, slot));
        if (scoreboard != null) this.viewers.forEach(viewer -> scoreboard.addViewer(viewer, slot));
    }

    @Override
    public boolean addViewer(IScoreboardViewer viewer) {
        var added =  this.viewers.add(viewer);
        if (added) this.display.forEach((slot, scoreboard) -> {
            if (scoreboard != null) scoreboard.addViewer(viewer, slot);
        });
        return added;
    }

    @Override
    public boolean removeViewer(IScoreboardViewer viewer) {
        var removed = viewers.remove(viewer);
        if (removed) this.display.forEach((slot, scoreboard) -> {
            if (scoreboard != null) scoreboard.removeViewer(viewer, slot);
        });
        return removed;
    }

    /**
     * You may be confused by the code here.
     * This actually exists to work around the "player offline" score entry caused by a player logging off.
     * When a player logs off, we remove the "player offline" entry from other online players' display slots
     * and add it back when they reconnect to the server.
     */

    @Override
    public void onPlayerJoin(Player player) {
        addViewer(player);
        var scorer = new PlayerScorer(player);
        this.scoreboards.values().forEach(scoreboard -> {
            if (scoreboard.containLine(scorer)) {
                this.viewers.forEach(viewer -> viewer.updateScore(scoreboard.getLine(scorer)));
            }
        });
    }

    @Override
    public void beforePlayerQuit(Player player) {
        var scorer = new PlayerScorer(player);
        this.scoreboards.values().forEach(scoreboard -> {
            if (scoreboard.containLine(scorer)) {
                this.viewers.forEach(viewer -> viewer.removeLine(scoreboard.getLine(scorer)));
            }
        });
        removeViewer(player);
    }

    @Override
    public void onEntityDead(EntityLiving entity) {
        var scorer = new EntityScorer(entity);
        this.scoreboards.forEach((s, scoreboard) -> {
            if (scoreboard.getLines().isEmpty()) return;
            scoreboard.removeLine(scorer);
        });
    }

    @Override
    public void save() {
        storage.removeAllScoreboard();
        storage.saveScoreboard(scoreboards.values());
        storage.saveDisplay(display);
    }

    @Override
    public void read() {
        //create a new list to avoid iteration conflicts
        new ArrayList<>(this.scoreboards.values()).forEach(this::removeScoreboard);
        this.display.forEach((slot, scoreboard) -> setDisplay(slot, null));

        scoreboards = storage.readScoreboard();
        storage.readDisplay().forEach((slot, objectiveName) -> {
            var scoreboard = getScoreboard(objectiveName);
            if (scoreboard != null) {
                this.setDisplay(slot, scoreboard);
            }
        });
    }
}
