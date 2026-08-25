package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;

/**
 * @author Kaooot
 */
public class LevelSoundEventHandler implements PacketHandler<LevelSoundEventPacket> {

    @Override
    public void handle(LevelSoundEventPacket packet, PlayerSessionHolder holder, Server server) {
        Player player = holder.getPlayer();
        if (!player.isSpectator()) {
            player.level.addChunkPacket(player.getChunkX(), player.getChunkZ(), packet);
        }
    }
}