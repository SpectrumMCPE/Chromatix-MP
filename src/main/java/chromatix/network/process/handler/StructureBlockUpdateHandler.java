package chromatix.network.process.handler;

import chromatix.PlayerHandle;
import chromatix.Server;
import chromatix.block.Block;
import chromatix.block.property.enums.StructureBlockType;
import chromatix.blockentity.BlockEntity;
import chromatix.blockentity.BlockEntityStructBlock;
import chromatix.math.Vector3;
import chromatix.network.process.PacketHandler;
import chromatix.network.process.PlayerSessionHolder;
import org.cloudburstmc.protocol.bedrock.packet.StructureBlockUpdatePacket;

import static chromatix.block.property.CommonBlockProperties.STRUCTURE_BLOCK_TYPE;

/**
 * @author Kaooot
 */
public class StructureBlockUpdateHandler implements PacketHandler<StructureBlockUpdatePacket> {

    @Override
    public void handle(StructureBlockUpdatePacket packet, PlayerSessionHolder holder, Server server) {
        PlayerHandle playerHandle = holder.getPlayerHandle();
        if (playerHandle.player.isOp() && playerHandle.player.isCreative()) {
            BlockEntity blockEntity = playerHandle.player.level.getBlockEntity(
                    new Vector3(
                            packet.getBlockPosition().getX(),
                            packet.getBlockPosition().getY(),
                            packet.getBlockPosition().getZ()
                    )
            );
            if (blockEntity instanceof BlockEntityStructBlock structBlock) {
                Block sBlock = structBlock.getLevelBlock();
                sBlock.setPropertyValue(STRUCTURE_BLOCK_TYPE, StructureBlockType.valueOf(packet.getStructureData().getStructureBlockType().name()));
                structBlock.updateSetting(packet);
                playerHandle.player.level.setBlock(structBlock, sBlock, true);
                structBlock.spawnTo(playerHandle.player);
            }
        }
    }
}