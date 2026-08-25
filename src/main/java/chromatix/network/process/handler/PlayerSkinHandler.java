package chromatix.network.process.handler;

import lombok.extern.slf4j.Slf4j;
import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;
import chromatix.Player;
import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.entity.data.human.Skin;
import chromatix.event.player.PlayerChangeSkinEvent;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import chromatix.utils.SkinConverter;
import chromatix.utils.SkinUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Kaooot
 */
@Slf4j
public class PlayerSkinHandler implements PacketHandler<PlayerSkinPacket> {

    @Override
    public void handle(PlayerSkinPacket packet, PlayerSessionHolder holder, Server server) {
        PlayerHandle playerHandle = holder.getPlayerHandle();
        Player player = playerHandle.player;
        boolean trusted = player.getServer().getSettings().playerSettings().forceSkinTrusted()
                || SkinConverter.isTrusted(packet.getSerializedSkin());
        Skin skin = new Skin(SkinConverter.fromSerializedSkin(packet.getSerializedSkin()), trusted);

        if (!player.spawned || !player.isAlive()) {
            log.debug("Player {} tried to update skin while not spawned or dead", playerHandle.getUsername());
            return;
        }

        if (!SkinUtils.isValid(skin.getSkin())) {
            log.warn("{}: PlayerSkinPacket with invalid skin", playerHandle.getUsername());
            return;
        }

        PlayerChangeSkinEvent playerChangeSkinEvent = new PlayerChangeSkinEvent(player, skin);
        var tooQuick = TimeUnit.SECONDS.toMillis(player.getServer().getSettings().playerSettings().skinChangeCooldown()) > System.currentTimeMillis() - player.lastSkinChange;
        if (tooQuick) {
            playerChangeSkinEvent.setCancelled(true);
            log.warn("Player {} change skin too quick!", playerHandle.getUsername());
        }
        player.getServer().getPluginManager().callEvent(playerChangeSkinEvent);
        if (!playerChangeSkinEvent.isCancelled()) {
            player.lastSkinChange = System.currentTimeMillis();
            player.setSkin(skin);
        }
    }
}
