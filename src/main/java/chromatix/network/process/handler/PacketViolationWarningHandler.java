package chromatix.network.process.handler;

import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.packet.PacketViolationWarningPacket;

/**
 * @author Kaooot
 */
@Slf4j
public class PacketViolationWarningHandler implements PacketHandler<PacketViolationWarningPacket> {

    @Override
    public void handle(PacketViolationWarningPacket packet, PlayerSessionHolder holder, Server server) {
        log.warn("Violation warning from {}: {}", holder.getPlayer().getName(), packet);
    }
}