package chromatix.network.process.handler;

import org.cloudburstmc.protocol.bedrock.packet.RequestChunkRadiusPacket;
import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;

/**
 * @author Kaooot
 */
public class RequestChunkRadiusHandler implements PacketHandler<RequestChunkRadiusPacket> {

    private static final int MIN_CHUNK_RADIUS = 2;

    @Override
    public void handle(RequestChunkRadiusPacket packet, PlayerSessionHolder holder, Server server) {
        final int viewDistance = server.getSettings().gameplaySettings().viewDistance();
        final int radius = Math.max(MIN_CHUNK_RADIUS, Math.min(packet.getChunkRadius(), viewDistance));
        holder.getPlayer().setViewDistance(radius);
    }
}
