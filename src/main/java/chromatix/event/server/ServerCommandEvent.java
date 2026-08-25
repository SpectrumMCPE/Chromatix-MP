package chromatix.event.server;

import chromatix.command.CommandSender;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


/**
 * @author MagicDroidX (Nukkit Project)
 */
public class ServerCommandEvent extends ServerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected String command;

    protected final CommandSender sender;

    public ServerCommandEvent(CommandSender sender, String command) {
        this.sender = sender;
        this.command = command;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
