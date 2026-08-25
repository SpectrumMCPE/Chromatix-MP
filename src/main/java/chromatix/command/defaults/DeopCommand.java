package chromatix.command.defaults;

import chromatix.IPlayer;
import chromatix.command.CommandSender;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.tree.node.IPlayersNode;
import chromatix.command.utils.CommandLogger;
import chromatix.utils.TextFormat;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;

import java.util.List;
import java.util.Map;

/**
 * @author xtypr
 * @since 2015/11/12
 */
public class DeopCommand extends VanillaCommand {
    public DeopCommand(String name) {
        super(name, "commands.deop.description");
        this.setPermission("nukkit.command.op.take");
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("player", CommandParamType.SELECTION, new IPlayersNode())
        });
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        List<IPlayer> IPlayers = result.getValue().getResult(0);
        if (IPlayers.isEmpty()) {
            log.addNoTargetMatch().output();
            return 0;
        }
        for (IPlayer player : IPlayers) {
            if (!player.isOp()) {
                log.addError("Privileges cannot be revoked (revoked or with higher privileges)").output();//no translation in client
                return 0;
            }
            player.setOp(false);
            if (player.isOnline()) {
                log.outputObjectWhisper(player.getPlayer(), TextFormat.GRAY + "%commands.deop.message");
            }
            log.addSuccess("commands.deop.success", player.getName()).output(true);
        }
        return IPlayers.size();
    }
}
