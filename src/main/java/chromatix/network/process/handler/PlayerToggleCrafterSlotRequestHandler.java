package chromatix.network.process.handler;

import chromatix.Server;
import chromatix.block.Block;
import chromatix.block.BlockCrafter;
import chromatix.blockentity.BlockEntityCrafter;
import chromatix.level.Level;
import chromatix.math.BlockVector3;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.PlayerToggleCrafterSlotRequestPacket;

/**
 * @author Kaooot
 */
public class PlayerToggleCrafterSlotRequestHandler implements PacketHandler<PlayerToggleCrafterSlotRequestPacket> {

    @Override
    public void handle(PlayerToggleCrafterSlotRequestPacket packet, PlayerSessionHolder holder, Server server) {
        Level level = holder.getPlayer().getLevel();
        BlockVector3 position = BlockVector3.fromNetwork(packet.getPos());
        Block block = level.getBlock(position.asVector3());
        if (!(block instanceof BlockCrafter crafter)) {
            return;
        }

        BlockEntityCrafter blockEntity = crafter.getOrCreateBlockEntity();
        int slot = packet.getSlotIndex();
        boolean state = !packet.isDisabled();

        blockEntity.getInventory().setSlotState(slot, state);
    }
}