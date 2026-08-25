package chromatix.entity.ai.executor;

import chromatix.block.Block;
import chromatix.block.Natural;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.mob.EntityEnderman;
import chromatix.item.Item;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorDataTypes;

import java.util.Arrays;
import java.util.Optional;

public class EndermanBlockExecutor implements IBehaviorExecutor {

    public boolean execute(EntityIntelligent entity) {
        if(entity instanceof EntityEnderman enderman) {
            if(enderman.getItemInHand().isNull()) {
                Optional<Block> optionalBlock = Arrays.stream(entity.level.getCollisionBlocks(entity.getBoundingBox().grow(3.7f, 0, 3.7f))).filter(block -> block instanceof Natural natural && natural.canBePickedUp()).findAny();
                if(optionalBlock.isPresent()) {
                    Block block = optionalBlock.get();
                    enderman.setItemInHand(block.toItem());
                    enderman.setDataProperty(ActorDataTypes.CARRY_BLOCK_RUNTIME_ID, block);
                    enderman.getLevel().setBlock(block, Block.get(Block.AIR));
                }
            } else {
                if(enderman.getItemInHand().isBlock()) {
                    Optional<Block> optionalBlock = Arrays.stream(entity.level.getCollisionBlocks(entity.getBoundingBox().addCoord(0.7f, -1, 0.7f))).filter(block -> block.isSolid() && block.up().canBeReplaced()).findAny();
                    if(optionalBlock.isPresent()) {
                        Block block = optionalBlock.get();
                        block.getLevel().setBlock(block.up(), enderman.getItemInHand().getBlock());
                        enderman.setItemInHand(Item.AIR);
                        enderman.setDataProperty(ActorDataTypes.CARRY_BLOCK_RUNTIME_ID, Item.AIR.getBlock());
                    }
                }   
            }
        }
        return true;
    }

}
