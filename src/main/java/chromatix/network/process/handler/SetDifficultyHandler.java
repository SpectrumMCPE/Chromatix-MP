package chromatix.network.process.handler;

import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.command.Command;
import chromatix.lang.TranslationContainer;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.SetDifficultyPacket;

/**
 * @author Kaooot
 */
public class SetDifficultyHandler implements PacketHandler<SetDifficultyPacket> {

    @Override
    public void handle(SetDifficultyPacket packet, PlayerSessionHolder holder, Server server) {
        PlayerHandle playerHandle = holder.getPlayerHandle();
        if (!playerHandle.player.spawned || !playerHandle.player.hasPermission("nukkit.command.difficulty")) {
            return;
        }
        server.setDifficulty(packet.getDifficulty());
        final SetDifficultyPacket difficultyPacket = new SetDifficultyPacket();
        difficultyPacket.setDifficulty(server.getDifficulty());
        Server.broadcastPacket(server.getOnlinePlayers().values(), difficultyPacket);
        Command.broadcastCommandMessage(playerHandle.player, new TranslationContainer("commands.difficulty.success", String.valueOf(server.getDifficulty())));
    }
}