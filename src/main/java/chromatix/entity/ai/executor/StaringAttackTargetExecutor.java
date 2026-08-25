package chromatix.entity.ai.executor;

import chromatix.Player;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.level.Sound;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;

public class StaringAttackTargetExecutor implements IBehaviorExecutor {

    @Override
    public boolean execute(EntityIntelligent entity) {
        if(entity.getMemoryStorage().isEmpty(CoreMemoryTypes.ATTACK_TARGET)) {
            if(!entity.getMemoryStorage().isEmpty(CoreMemoryTypes.STARING_PLAYER) && new EntityCheckEvaluator(CoreMemoryTypes.STARING_PLAYER).evaluate(entity)) {
                entity.getMemoryStorage().put(CoreMemoryTypes.ATTACK_TARGET, entity.getMemoryStorage().get(CoreMemoryTypes.STARING_PLAYER));
                entity.level.addSound(entity, Sound.MOB_ENDERMEN_STARE);
            } else if(!entity.getMemoryStorage().isEmpty(CoreMemoryTypes.NEAREST_ENDERMITE)) {
                entity.getMemoryStorage().put(CoreMemoryTypes.ATTACK_TARGET, entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_ENDERMITE));
            }
        } else {
            if(entity.getMemoryStorage().get(CoreMemoryTypes.ATTACK_TARGET) instanceof Player player) {
                if(!player.isOnline()) {
                    entity.getMemoryStorage().clear(CoreMemoryTypes.STARING_PLAYER);
                    entity.getMemoryStorage().clear(CoreMemoryTypes.ATTACK_TARGET);
                }
            }
        }
        if(entity.getMemoryStorage().isEmpty(CoreMemoryTypes.ATTACK_TARGET)) {
            if(entity.getDataFlag(ActorFlags.ANGRY)) {
                entity.setDataFlag(ActorFlags.ANGRY, false);
            }
        } else {
            if(new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET).evaluate(entity)) {
                if (!entity.getDataFlag(ActorFlags.ANGRY)) {
                    entity.setDataFlag(ActorFlags.ANGRY);
                }
            } else entity.getMemoryStorage().clear(CoreMemoryTypes.ATTACK_TARGET);
        }
        return true;
    }
}
