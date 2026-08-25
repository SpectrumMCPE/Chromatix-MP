package chromatix.entity.item;

import chromatix.entity.Entity;
import chromatix.entity.effect.Effect;
import chromatix.entity.effect.PotionApplicationMode;
import chromatix.entity.effect.PotionType;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.DoubleTag;
import chromatix.nbt.tag.FloatTag;
import chromatix.nbt.tag.ListTag;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class EntityLingeringPotion extends EntitySplashPotion {
    @Override
    @NotNull
    public String getIdentifier() {
        return LINGERING_POTION;
    }

    public EntityLingeringPotion(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public EntityLingeringPotion(IChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        setDataFlag(ActorFlags.LINGERING, true);
    }

    @Override
    protected void splash(Entity collidedWith) {
        super.splash(collidedWith);
        saveNBT();
        ListTag<?> pos = (ListTag<?>) nbt.getList("Pos", CompoundTag.class).copy();
        EntityAreaEffectCloud entity = (EntityAreaEffectCloud) Entity.createEntity(Entity.AREA_EFFECT_CLOUD, getChunk(),
                new CompoundTag().putList("Pos", pos)
                        .putList("Rotation", new ListTag<>()
                                .add(new FloatTag(0))
                                .add(new FloatTag(0))
                        )
                        .putList("Motion", new ListTag<>()
                                .add(new DoubleTag(0))
                                .add(new DoubleTag(0))
                                .add(new DoubleTag(0))
                        )
                        .putShort("PotionId", potionId)
        );

        List<Effect> effects = PotionType.get(potionId).getEffects(PotionApplicationMode.LINGERING);
        for (Effect effect : effects) {
            if (effect != null && entity != null) {
                entity.cloudEffects.add(effect/*.setDuration(1)*/.setVisible(false).setAmbient(false));
                entity.spawnToAll();
            }
        }
    }

    @Override
    public String getOriginalName() {
        return "Lingering Potion";
    }
}