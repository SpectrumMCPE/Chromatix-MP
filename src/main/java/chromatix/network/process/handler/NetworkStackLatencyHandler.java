package chromatix.network.process.handler;

import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.NetworkStackLatencyPacket;

/**
 * @author Kaooot
 */
public class NetworkStackLatencyHandler implements PacketHandler<NetworkStackLatencyPacket> {

    @Override
    public boolean runsOnNetworkThread() {
        return true; // deferring to the tick would skew the RTT measurement
    }

    @Override
    public void handle(NetworkStackLatencyPacket packet, PlayerSessionHolder holder, Server server) {
        final PlayerHandle playerHandle = holder.getPlayerHandle();

        playerHandle.onAckReceive(packet.getCreationTime());

        if (packet.isFromServer()) {
            // client-initiated ping: echo it back
            final NetworkStackLatencyPacket response = new NetworkStackLatencyPacket();
            response.setCreationTime(packet.getCreationTime());
            response.setFromServer(false);
            holder.getSession().sendPacketImmediately(response);
        }
    }
}
