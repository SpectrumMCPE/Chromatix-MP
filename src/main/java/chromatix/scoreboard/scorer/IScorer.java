package chromatix.scoreboard.scorer;

import org.cloudburstmc.protocol.bedrock.data.payload.scoreboard.ScoreInfo;
import org.cloudburstmc.protocol.bedrock.data.payload.scoreboard.ScorePacketEntryAction;
import chromatix.scoreboard.IScoreboard;
import chromatix.scoreboard.IScoreboardLine;

public interface IScorer {

    ScorePacketEntryAction getScorerType();

    String getName();

    ScoreInfo toNetworkInfo(IScoreboard scoreboard, IScoreboardLine line);
}
