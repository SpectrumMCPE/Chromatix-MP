package chromatix.command.defaults;

import chromatix.command.CommandSender;
import chromatix.command.data.CommandEnum;
import chromatix.command.data.CommandParameter;
import chromatix.command.tree.ParamList;
import chromatix.command.utils.CommandLogger;
import chromatix.level.GameRule;
import chromatix.level.GameRules;
import chromatix.level.Level;

import java.util.Map;


public class DayLockCommand extends VanillaCommand {

    public DayLockCommand(String name) {
        super(name, "commands.daylock.description", "", new String[]{"alwaysday"});
        this.setPermission("nukkit.command.daylock");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newEnum("lock", true, CommandEnum.ENUM_BOOLEAN)
        });
        this.enableParamTree();
    }

    @Override
    public int execute(CommandSender sender, String commandLabel, Map.Entry<String, ParamList> result, CommandLogger log) {
        var list = result.getValue();
        boolean lock = true;

        if (list.hasResult(0)) lock = list.getResult(0);

        Level level = sender.getPosition().getLevel();
        level = level == null ? sender.getServer().getDefaultLevel() : level;
        GameRules rules = level.getGameRules();

        if (lock) {
            rules.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            level.stopTime();
            level.setTime(5000);
            log.addSuccess("commands.always.day.locked").output();
        } else {
            rules.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
            level.startTime();
            log.addSuccess("commands.always.day.unlocked").output();
        }
        return 1;
    }
}
