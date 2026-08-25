package chromatix.command.defaults;

import chromatix.Player;
import chromatix.Server;
import chromatix.command.CommandSender;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.tree.node.PlayersNode;
import chromatix.command.utils.CommandLogger;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class ClearSpawnPointCommand extends VanillaCommand {

    public ClearSpawnPointCommand(String name) {
        super(name, "commands.clearspawnpoint.description");
        this.setPermission("nukkit.command.clearspawnpoint");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newType("player", true, CommandParamType.SELECTION, new PlayersNode()),
        });
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        var list = result.getValue();
        List<Player> players = sender.isPlayer() ? List.of(sender.asPlayer()) : List.of();
        if (list.hasResult(0)) players = list.getResult(0);
        players = players.stream().filter(Objects::nonNull).toList();
        if (players.isEmpty()) {
            log.addNoTargetMatch().output();
            return 0;
        }
        for (Player player : players) {
            player.setSpawn(Server.getInstance().getDefaultLevel().getSpawnLocation(), Player.SpawnPointType.WORLD);
        }
        String players_str = players.stream().map(Player::getName).collect(Collectors.joining(" "));
        if (players.size() > 1) {
            log.addSuccess("commands.clearspawnpoint.success.multiple", players_str);
        } else {
            log.addSuccess("commands.clearspawnpoint.success.single", players_str);
        }
        log.successCount(players.size()).output();
        return players.size();
    }
}
