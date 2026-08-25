package chromatix.command.defaults;

import chromatix.command.CommandSender;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.utils.CommandLogger;

import java.util.Map;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class StopCommand extends VanillaCommand {

    public StopCommand(String name) {
        super(name, "commands.stop.description");
        this.setPermission("nukkit.command.stop");
        this.commandParameters.clear();
        this.commandParameters.put("default", CommandParameter.EMPTY_ARRAY);
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        log.addSuccess("commands.stop.start").output(true);
        sender.getServer().shutdown();
        return 1;
    }
}
