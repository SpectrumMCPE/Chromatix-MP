package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.entity.EntityFlyable;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.EntitySmite;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LiftController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.SpaceMoveController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import chromatix.entity.ai.evaluator.PassByTimeEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.CircleAboveTargetExecutor;
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
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author PetteriM1
 */
public class EntityPhantom extends EntityMob implements EntityFlyable, EntitySmite {

    @Override
    @NotNull public String getIdentifier() {
        return PHANTOM;
    }

    public EntityPhantom(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_PHANTOM_IDLE, 0.8f, 1.2f, 0.8f, 0.8f), all(new RandomSoundEvaluator()), 6, 1),
                        new Behavior(new CircleAboveTargetExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.4f, true), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.LAST_ATTACK_ENTITY),
                                not(new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, Utils.rand(200, 400)))
                        ), 5, 1),
                        new Behavior(new CircleAboveTargetExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.4f, true), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.LAST_ATTACK_ENTITY),
                                not(new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, Utils.rand(200, 400)))
                        ), 4, 1),
                        new Behavior(new PhantomMeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.5f, 64, false, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 3, 1),
                        new Behavior(new PhantomMeleeAttackExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.5f, 64, false, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), 2, 1),
                        new Behavior(new SpaceRandomRoamExecutor(0.15f, 12, 100, 20, false, -1, true, 10), (entity -> true), 1, 1)
                )
                .sensors(
                        new NearestPlayerSensor(64, 0, 20),
                        new NearestTargetEntitySensor<>(0, 64, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget)
                )
                .controllers(new SpaceMoveController(), new LookController(true, true), new LiftController())
                .routeFinder(new SimpleSpaceAStarRouteFinder(new FlyingPosEvaluator(), this))
                .build();
    }

    @Override
    public boolean isEnablePitch() {
        return false;
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.diffHandDamage = new float[]{4f, 6f, 9f};

    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(20);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.1f);
    }

    @Override
    public String getOriginalName() {
        return "Phantom";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("phantom", "undead", "monster", "mob");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);

        if (Utils.rand(0, 1) == 0) {
            return Item.EMPTY_ARRAY;
        }

        int amount = Utils.rand(0, 1 + looting);
        if (amount <= 0) {
            return Item.EMPTY_ARRAY;
        }

        return new Item[]{
                Item.get(ItemID.PHANTOM_MEMBRANE, 0, amount)
        };
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        burn(this);
        return super.onUpdate(currentTick);
    }

    private static class PhantomMeleeAttackExecutor extends MeleeAttackExecutor {

        public PhantomMeleeAttackExecutor(MemoryType<? extends Entity> memory, float speed, int maxSenseRange, boolean clearDataWhenLose, int coolDown) {
            super(memory, speed, maxSenseRange, clearDataWhenLose, coolDown, 2.5f);
        }

        @Override
        public void onStart(EntityIntelligent entity) {
            super.onStart(entity);
            entity.level.addSound(entity, Sound.MOB_PHANTOM_SWOOP);
        }
    }
}
