package chromatix.network.process.handler;

import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.block.Block;
import chromatix.block.BlockLectern;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityLectern;
import chromatix.event.block.LecternPageChangeEvent;
import chromatix.math.BlockVector3;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.LecternUpdatePacket;

/**
 * @author Kaooot
 */
public class LecternUpdateHandler implements PacketHandler<LecternUpdatePacket> {

    @Override
    public void handle(LecternUpdatePacket packet, PlayerSessionHolder holder, Server server) {
        final PlayerHandle playerHandle = holder.getPlayerHandle();
        BlockVector3 blockPosition = BlockVector3.fromNetwork(packet.getPositionOfLecternToUpdate());
        playerHandle.player.temporalVector.setComponents(blockPosition.x, blockPosition.y, blockPosition.z);
        BlockEntity blockEntityLectern = playerHandle.player.level.getBlockEntity(playerHandle.player.temporalVector);
        if (blockEntityLectern instanceof BlockEntityLectern lectern) {
            LecternPageChangeEvent lecternPageChangeEvent = new LecternPageChangeEvent(playerHandle.player, lectern, packet.getNewPageToShow());
            playerHandle.player.getServer().getPluginManager().callEvent(lecternPageChangeEvent);
            if (!lecternPageChangeEvent.isCancelled()) {
                lectern.setRawPage(lecternPageChangeEvent.getNewRawPage());
                lectern.spawnToAll();
                Block blockLectern = lectern.getBlock();
                if (blockLectern instanceof BlockLectern) {
                    ((BlockLectern) blockLectern).executeRedstonePulse();
                }
            }
        }
    }
}