package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.entity.EntityFlyable;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LiftController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.SpaceMoveController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.PassByTimeEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.MeleeAttackExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.executor.SpaceRandomRoamExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.memory.MemoryType;
import chromatix.entity.ai.route.finder.impl.SimpleSpaceAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.FlyingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.ai.sensor.NearestTargetEntitySensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.passive.EntityVillagerV2;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.item.Item;
import chromatix.item.ItemTool;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.Setter;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author PikyCZ
 */
public class EntityVex extends EntityMob implements EntityFlyable {


    @Override
    @NotNull public String getIdentifier() {
        return VEX;
    }

    public EntityVex(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Getter
    @Setter
    private EntityEvocationIllager illager;
    private int start_damage_timer = ThreadLocalRandom.current().nextInt(30, 120);


    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_VEX_AMBIENT), new RandomSoundEvaluator(), 5, 1),
                        new Behavior(new VexMeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 30), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 80, Integer.MAX_VALUE)
                        ), 4, 1),
                        new Behavior(new VexMeleeAttackExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.3f, 40, true, 30), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 80, Integer.MAX_VALUE)
                        ), 3, 1),
                        new Behavior(new VexMeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, false, 30), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 80, Integer.MAX_VALUE)
                        ), 2, 1),
                        new Behavior(new SpaceRandomRoamExecutor(0.15f, 12, 100, 20, false, -1, true, 10), (entity -> true), 1, 1)
                )
                .sensors(
                        new NearestPlayerSensor(70, 0, 20),
                        new NearestTargetEntitySensor<>(0, 70, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget)
                )
                .controllers(new SpaceMoveController(), new LookController(true, true), new LiftController())
                .routeFinder(new SimpleSpaceAStarRouteFinder(new FlyingPosEvaluator(), this))
                .build();
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return switch (entity.getIdentifier()) {
            case VILLAGER ->
                entity instanceof EntityVillagerV2 villager && !villager.isBaby();
            case IRON_GOLEM, WANDERING_TRADER -> true;
            default -> false;
        };
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        getMemoryStorage().put(CoreMemoryTypes.LAST_ATTACK_TIME, getLevel().getTick());
        this.setItemInHand(Item.get(Item.IRON_SWORD));
        this.diffHandDamage = new float[]{5.5f, 9f, 13.5f};
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if(source.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            return false;
        }
        return super.attack(source);
    }

    @Override
    public float getWidth() {
        return 0.4f;
    }

    @Override
    public float getHeight() {
        return 0.8f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(14);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(1.0f);
    }

    @Override
    public String getOriginalName() {
        return "Vex";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("vex", "monster", "mob");
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        if(getItemInHand() instanceof ItemTool tool) {
            tool.setDamage(ThreadLocalRandom.current().nextInt(tool.getMaxDurability()));
            return new Item[] {
                tool
            };
        }
        return super.getDrops(weapon);
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (closed) return true;
        if (getIllager() != null) {
            if (this.distanceSquared(illager) > 56.25d) {
                setMoveTarget(illager);
                setLookTarget(illager);
            }
            if (ticksLived % 20 == 0 && ticksLived >= start_damage_timer * 20) {
                this.attack(new EntityDamageEvent(this, EntityDamageEvent.DamageCause.AGE, 1));
            }
        }
        return super.onUpdate(currentTick);
    }

    private class VexMeleeAttackExecutor extends MeleeAttackExecutor {

        public VexMeleeAttackExecutor(MemoryType<? extends Entity> memory, float speed, int maxSenseRange, boolean clearDataWhenLose, int coolDown) {
            super(memory, speed, maxSenseRange, clearDataWhenLose, coolDown, 1);
        }

        @Override
        public void onStart(EntityIntelligent entity) {
            super.onStart(entity);
            entity.setDataFlag(ActorFlags.CHARGING);
            entity.level.addSound(entity, Sound.MOB_VEX_CHARGE);
        }

        @Override
        public void onStop(EntityIntelligent entity) {
            super.onStop(entity);
            entity.setDataFlag(ActorFlags.CHARGING, false);
        }

        @Override
        public void onInterrupt(EntityIntelligent entity) {
            super.onInterrupt(entity);
            entity.setDataFlag(ActorFlags.CHARGING, false);
        }
    }

}
