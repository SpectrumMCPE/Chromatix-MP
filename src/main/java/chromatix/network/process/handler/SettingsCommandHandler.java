package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.SettingsCommandPacket;

import java.util.Locale;

/**
 * @author Kaooot
 */
public class SettingsCommandHandler implements PacketHandler<SettingsCommandPacket> {

    @Override
    public void handle(SettingsCommandPacket packet, PlayerSessionHolder holder, Server server) {
        Player player = holder.getPlayer();
        String command = packet.getCommand().toLowerCase(Locale.ENGLISH);
        player.getServer().executeCommand(player, command);
    }
}