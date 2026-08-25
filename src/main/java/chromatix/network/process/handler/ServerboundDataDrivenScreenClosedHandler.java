package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.Server;
import chromatix.ddui.DataDrivenScreen;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDataDrivenScreenClosedPacket;

/**
 * @author Kaooot
 */
public class ServerboundDataDrivenScreenClosedHandler implements PacketHandler<ServerboundDataDrivenScreenClosedPacket> {

    @Override
    public void handle(ServerboundDataDrivenScreenClosedPacket packet, PlayerSessionHolder holder, Server server) {
        Player player = holder.getPlayer();
        DataDrivenScreen screen = DataDrivenScreen.getScreenByFormId(player, packet.getFormId());
        if (screen != null) {
            screen.removeViewer(player);
        }
    }
}
