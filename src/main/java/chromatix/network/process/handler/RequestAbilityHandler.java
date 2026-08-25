package chromatix.network.process.handler;

import chromatix.AdventureSettings;
import chromatix.Player;
import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.event.player.PlayerHackDetectedEvent;
import chromatix.event.player.PlayerKickEvent;
import chromatix.event.player.PlayerToggleFlightEvent;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.data.AbilitiesIndex;
import org.cloudburstmc.protocol.bedrock.packet.RequestAbilityPacket;

/**
 * @author Kaooot
 */
@Slf4j
public class RequestAbilityHandler implements PacketHandler<RequestAbilityPacket> {

    @Override
    public void handle(RequestAbilityPacket packet, PlayerSessionHolder holder, Server server) {
        PlayerHandle playerHandle = holder.getPlayerHandle();
        Player player = playerHandle.player;
        AbilitiesIndex ability = packet.getAbility();
        if (ability != AbilitiesIndex.FLYING) {
//            log.info("[{}] has tried to trigger {} ability {}", player.getName(), ability, packet.isBoolValue() ? "on" : "off");
            return;
        }

        if (!packet.isBoolValue() && !player.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT)) {
            PlayerHackDetectedEvent detectedEvent = new PlayerHackDetectedEvent(player, PlayerHackDetectedEvent.HackType.FLIGHT);
            Server.getInstance().getPluginManager().callEvent(detectedEvent);
            if (detectedEvent.isKick())
                player.kick(PlayerKickEvent.Reason.FLYING_DISABLED, "Flying is not enabled on this server");
            return;
        }

        PlayerToggleFlightEvent playerToggleFlightEvent = new PlayerToggleFlightEvent(player, packet.isBoolValue());
        player.getServer().getPluginManager().callEvent(playerToggleFlightEvent);
        if (playerToggleFlightEvent.isCancelled()) {
            player.getAdventureSettings().update();
        } else {
            player.getAdventureSettings().set(AdventureSettings.Type.FLYING, playerToggleFlightEvent.isFlying());
        }
    }
}