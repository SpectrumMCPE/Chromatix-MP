package chromatix.network.process.handler;

import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import chromatix.network.process.SessionState;
import org.cloudburstmc.protocol.bedrock.data.DisconnectFailReason;
import org.cloudburstmc.protocol.bedrock.data.PlayStatus;
import org.cloudburstmc.protocol.bedrock.packet.ClientToServerHandshakePacket;

/**
 * @author Kaooot
 */
public class ClientToServerHandshakeHandler implements PacketHandler<ClientToServerHandshakePacket> {

    @Override
    public void handle(ClientToServerHandshakePacket packet, PlayerSessionHolder holder, Server server) {
        if (!holder.getState().equals(SessionState.ENCRYPTION)) {
            holder.disconnect(DisconnectFailReason.UNEXPECTED_PACKET);
            return;
        }
        holder.sendPlayStatus(PlayStatus.LOGIN_SUCCESS);
        holder.setState(SessionState.RESOURCE_PACK);
        holder.sendResourcePacksInfo(server);
    }
}