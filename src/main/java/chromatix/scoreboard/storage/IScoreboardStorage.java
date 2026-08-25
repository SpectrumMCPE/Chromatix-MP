package chromatix.scoreboard.storage;

import chromatix.scoreboard.data.DisplaySlot;
import chromatix.scoreboard.IScoreboard;

import java.util.Collection;
import java.util.Map;

/**
 * Scoreboard storage interface.
 */


public interface IScoreboardStorage {
    void saveScoreboard(IScoreboard scoreboard);

    void saveScoreboard(Collection<IScoreboard> scoreboards);

    void saveDisplay(Map<DisplaySlot, IScoreboard> display);

    Map<String, IScoreboard> readScoreboard();

    IScoreboard readScoreboard(String name);

    Map<DisplaySlot, String> readDisplay();

    void removeScoreboard(String name);

    void removeAllScoreboard();

    boolean containScoreboard(String name);
}
