package chromatix.entity.ai.executor;

import chromatix.Server;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.effect.Effect;
import chromatix.entity.effect.EffectType;
import chromatix.entity.mob.EntityZombiePigman;
import chromatix.event.entity.EntityTransformEvent;
import chromatix.inventory.Inventory;
import chromatix.level.Sound;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;

public class PiglinTransformExecutor implements EntityControl, IBehaviorExecutor {

    protected int tick = 0;

    public PiglinTransformExecutor() {}

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
        EntityZombiePigman entityZombiePigman = new EntityZombiePigman(entity.getChunk(), entity.getNbt());
        EntityTransformEvent event = new EntityTransformEvent(entity, entityZombiePigman);
        Server.getInstance().getPluginManager().callEvent(event);
        if(event.isCancelled()) {
            entityZombiePigman.close();
        } else {
            entity.close();
            entityZombiePigman.spawnToAll();
            entityZombiePigman.level.addSound(entityZombiePigman, Sound.MOB_PIGLIN_CONVERTED_TO_ZOMBIFIED);
            Inventory inventory = entityZombiePigman.getEquipmentInventory();
            for (int i = 2; i < inventory.getSize(); i++) {
                entityZombiePigman.level.dropItem(entityZombiePigman, inventory.getItem(i));
                inventory.clear(i);
            }
            entityZombiePigman.addEffect(Effect.get(EffectType.NAUSEA).setDuration(15));
        }
    }

}


