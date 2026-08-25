package chromatix.network.process.handler;

import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;

/**
 * @author Kaooot
 */
public class SetLocalPlayerAsInitializedHandler implements PacketHandler<SetLocalPlayerAsInitializedPacket> {

    @Override
    public void handle(SetLocalPlayerAsInitializedPacket packet, PlayerSessionHolder holder, Server server) {
        holder.getPlayer().onPlayerLocallyInitialized();
    }
}