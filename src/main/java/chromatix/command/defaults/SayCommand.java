package chromatix.command.defaults;

import chromatix.command.CommandSender;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.utils.CommandLogger;
import chromatix.lang.TranslationContainer;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParamType;

import java.util.Map;

/**
 * @author xtypr
 * @since 2015/11/12
 */
public class SayCommand extends VanillaCommand {

    public SayCommand(String name) {
        super(name, "commands.say.description");
        this.setPermission("nukkit.command.say");
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                CommandParameter.newType("message", CommandParamType.MESSAGE)
        });
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        String senderString = sender.getViewableName(null);
        String message = result.getValue().getResult(0);
        sender.getServer().broadcastMessage(new TranslationContainer("%chat.type.announcement", senderString, message));
        return 1;
    }
}
