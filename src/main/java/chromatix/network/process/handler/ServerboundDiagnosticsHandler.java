package chromatix.network.process.handler;

import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;

/**
 * @author Kaooot
 */
public class ServerboundDiagnosticsHandler implements PacketHandler<ServerboundDiagnosticsPacket> {

    @Override
    public void handle(ServerboundDiagnosticsPacket packet, PlayerSessionHolder holder, Server server) {

    }
}