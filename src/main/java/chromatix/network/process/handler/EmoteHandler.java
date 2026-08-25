package chromatix.network.process.handler;

import chromatix.Player;
import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import chromatix.utils.UUIDValidator;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.data.EmoteFlag;
import org.cloudburstmc.protocol.bedrock.packet.EmotePacket;

/**
 * @author Kaooot
 */
@Slf4j
public class EmoteHandler implements PacketHandler<EmotePacket> {

    @Override
    public void handle(EmotePacket packet, PlayerSessionHolder holder, Server server) {
        final PlayerHandle playerHandle = holder.getPlayerHandle();
        if (!playerHandle.player.spawned) {
            return;
        }
        if (packet.getActorRuntimeId() != playerHandle.player.getId()) {
            log.warn("{} sent EmotePacket with invalid entity id: {} != {}", playerHandle.getUsername(), packet.getActorRuntimeId(), playerHandle.player.getId());
            return;
        }
        if (!UUIDValidator.isValidUUID(packet.getEmoteId())) {
            log.warn("{} sent EmotePacket with invalid emoteId: {}", playerHandle.getUsername(), packet.getEmoteId());
            return;
        }

        packet.getFlags().clear();
        packet.getFlags().add(EmoteFlag.SERVER_SIDE);
        packet.getFlags().add(EmoteFlag.MUTE_EMOTE_CHAT);
        for (Player viewer : playerHandle.player.getViewers().values()) {
            viewer.sendPacket(packet);
        }
    }
}