package chromatix.command.defaults;

import chromatix.command.Command;
import chromatix.command.CommandSender;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.utils.CommandLogger;
import chromatix.plugin.Plugin;
import chromatix.utils.TextFormat;

import java.util.Map;

/**
 * @author xtypr
 * @since 2015/11/12
 */
public class PluginsCommand extends Command implements CoreCommand {

    public PluginsCommand(String name) {
        super(name,
                "%nukkit.command.plugins.description",
                "%nukkit.command.plugins.usage",
                new String[]{"pl"}
        );
        this.setPermission("nukkit.command.plugins");
        this.commandParameters.clear();
        this.commandParameters.put("default", CommandParameter.EMPTY_ARRAY);
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        this.sendPluginList(sender, log);
        return 1;
    }

    private void sendPluginList(CommandSender sender, CommandLogger log) {
        StringBuilder list = new StringBuilder();
        Map<String, Plugin> plugins = sender.getServer().getPluginManager().getPlugins();
        for (Plugin plugin : plugins.values()) {
            if (list.length() > 0) {
                list.append(TextFormat.WHITE + ", ");
            }
            list.append(plugin.isEnabled() ? TextFormat.GREEN : TextFormat.RED);
            list.append(plugin.getDescription().getFullName());
        }
        log.addMessage("nukkit.command.plugins.success", String.valueOf(plugins.size()), list.toString()).output();
    }
}
