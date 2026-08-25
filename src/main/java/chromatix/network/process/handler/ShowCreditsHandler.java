package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.event.player.PlayerTeleportEvent;
import chromatix.level.Position;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import chromatix.utils.PortalHelper;
import org.cloudburstmc.protocol.bedrock.packet.ShowCreditsPacket;

/**
 * @author Kaooot
 */
public class ShowCreditsHandler implements PacketHandler<ShowCreditsPacket> {

    @Override
    public void handle(ShowCreditsPacket packet, PlayerSessionHolder holder, Server server) {
        if (packet.getCreditsState().equals(ShowCreditsPacket.CreditsState.END_CREDITS)) {
            PlayerHandle playerHandle = holder.getPlayerHandle();
            if (playerHandle.getShowingCredits()) {
                playerHandle.player.setShowingCredits(false);
                Position spawn;
                if (playerHandle.player.getSpawn().right() == Player.SpawnPointType.WORLD) {
                    spawn = PortalHelper.convertPosBetweenEndAndOverworld(playerHandle.player.getLocation());
                } else spawn = playerHandle.player.getSpawn().left();
                if (spawn != null) {
                    playerHandle.player.teleport(spawn, PlayerTeleportEvent.TeleportCause.END_PORTAL);
                }
            }
        }
    }
}