package chromatix.entity.ai.executor;

import chromatix.Server;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.effect.Effect;
import chromatix.entity.effect.EffectType;
import chromatix.entity.mob.EntityZoglin;
import chromatix.event.entity.EntityTransformEvent;
import chromatix.level.Sound;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;

public class HoglinTransformExecutor implements EntityControl, IBehaviorExecutor {

    protected int tick = 0;

    public HoglinTransformExecutor() {}

    @Override
    public boolean execute(EntityIntelligent entity) {
        tick++;
        if(tick >= 300) {
            transform(entity);
            return false;
        }
        return true;
    }


    @Override
    public void onStart(EntityIntelligent entity) {
        tick = -1;
        entity.setDataFlag(ActorFlags.SHAKING);
    }

    @Override
    public void onStop(EntityIntelligent entity) {
        entity.setDataFlag(ActorFlags.SHAKING, false);
    }

    @Override
    public void onInterrupt(EntityIntelligent entity) {
        onStop(entity);
    }

    private void transform(EntityIntelligent entity) {
        entity.saveNBT();
        EntityZoglin zoglin = new EntityZoglin(entity.getChunk(), entity.getNbt());
        EntityTransformEvent event = new EntityTransformEvent(entity, zoglin);
        Server.getInstance().getPluginManager().callEvent(event);
        if(event.isCancelled()) {
            zoglin.close();
        } else {
            entity.close();
            zoglin.spawnToAll();
            zoglin.level.addSound(zoglin, Sound.MOB_HOGLIN_CONVERTED_TO_ZOMBIFIED);
            zoglin.addEffect(Effect.get(EffectType.NAUSEA).setDuration(15));
        }
    }

}


