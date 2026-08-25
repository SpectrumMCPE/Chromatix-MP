package chromatix.entity.effect;

import chromatix.entity.Entity;
import chromatix.entity.EntityID;
import chromatix.entity.mob.EntitySlime;
import chromatix.nbt.tag.CompoundTag;

import java.awt.*;

public class EffectOozing extends Effect {

    private static final int SLIME_COUNT = 2;

    public EffectOozing() {
        super(EffectType.OOZING, "%effect.oozing", new Color(153, 255, 163), true);
    }

    @Override
    public void onDeath(Entity entity) {
        for (int i = 0; i < SLIME_COUNT; i++) {
            CompoundTag nbt = Entity.getDefaultNBT(entity.getPosition());
            nbt.putInt("SlimeSize", EntitySlime.SIZE_MEDIUM);
            Entity slime = Entity.createEntity(EntityID.SLIME, entity.getChunk(), nbt);
            if (slime != null) {
                slime.spawnToAll();
            }
        }
    }
}
