package chromatix.entity.ai.executor.evocation;

import chromatix.entity.Entity;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.mob.EntityEvocationIllager;
import chromatix.entity.passive.EntitySheep;
import chromatix.level.Sound;
import chromatix.utils.BlockColor;
import chromatix.utils.DyeColor;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorDataTypes;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;

import static chromatix.entity.ai.memory.CoreMemoryTypes.LAST_MAGIC;

public class ColorConversionExecutor extends FangLineExecutor {

    //Values represent ticks
    private final static int CAST_DURATION = 60;

    public ColorConversionExecutor() {}
    @Override
    public boolean execute(EntityIntelligent entity) {
        tick++;
        if(tick == CAST_DURATION) {
            for(Entity entity1 : entity.getLevel().getNearbyEntities(entity.getBoundingBox().grow(16, 16, 16))) {
                if(entity1 instanceof EntitySheep entitySheep) {
                    if(entitySheep.getColor() == DyeColor.BLUE.getWoolData()) {
                        entitySheep.setColor(DyeColor.RED.getWoolData());
                    }
                }
            }
        }
        if(tick >= CAST_DURATION) {
            int tick = entity.getLevel().getTick();
            entity.getMemoryStorage().put(CoreMemoryTypes.LAST_CONVERSION, tick);
            entity.getMemoryStorage().put(CoreMemoryTypes.LAST_ATTACK_TIME, tick);
            return false;
        } else return true;
    }

    @Override
    protected void startSpell(EntityIntelligent entity) {
        tick = 0;
        entity.level.addSound(entity, Sound.MOB_EVOCATION_ILLAGER_PREPARE_WOLOLO);
        entity.setDataProperty(ActorDataTypes.DATA_SPELL_CASTING_COLOR, BlockColor.ORANGE_BLOCK_COLOR.getARGB());
        entity.getMemoryStorage().put(LAST_MAGIC, EntityEvocationIllager.SPELL.COLOR_CONVERSION);
        entity.setDataFlag(ActorFlags.CASTING);
    }
}
