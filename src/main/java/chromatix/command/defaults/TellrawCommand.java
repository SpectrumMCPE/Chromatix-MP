package chromatix.command.defaults;

import chromatix.Player;
import chromatix.command.CommandSender;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.tree.node.PlayersNode;
import chromatix.command.utils.CommandLogger;
import chromatix.command.utils.RawText;
import chromatix.lang.TranslationContainer;
import chromatix.utils.TextFormat;
import com.google.gson.JsonSyntaxException;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TellrawCommand extends VanillaCommand {

    public TellrawCommand(String name) {
        super(name, "commands.tellraw.description");
        this.setPermission("nukkit.command.tellraw");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.SELECTION, new PlayersNode()),
                CommandParameter.newType("rawtext", CommandParamType.RAW_TEXT)
        });
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        var list = result.getValue();
        try {
            List<Player> players = list.getResult(0);
            players = players.stream().filter(Objects::nonNull).toList();
            if (players.isEmpty()) {
                log.addNoTargetMatch().output();
                return 0;
            }
            RawText rawTextObject = list.getResult(1);
            rawTextObject.preParse(sender);
            for (Player player : players) {
                player.sendRawTextMessage(rawTextObject);
            }
            return 1;
        } catch (JsonSyntaxException e) {
            sender.sendMessage(new TranslationContainer(TextFormat.RED + "%commands.tellraw.jsonStringException"));
            return 0;
        }
    }
}
