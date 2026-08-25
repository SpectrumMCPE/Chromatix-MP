package chromatix.entity.mob;

import chromatix.block.BlockDoor;
import chromatix.entity.Entity;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.PiglinTransformExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.BlockSensor;
import chromatix.entity.ai.sensor.NearestEntitySensor;
import chromatix.entity.ai.sensor.NearestPlayerAngryPiglinSensor;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.ai.sensor.NearestTargetEntitySensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.item.Item;
import chromatix.level.Level;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author joserobjr
 * @since 2020-11-20
 */


public class EntityPiglinBrute extends EntityPiglin implements EntityWalkable {


    @Override
    @NotNull public String getIdentifier() {
        return PIGLIN_BRUTE;
    }

    public EntityPiglinBrute(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(50);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.35f);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new PiglinTransformExecutor(), all(
                                entity -> entity.getLevel().getDimension() != Level.DIMENSION_NETHER,
                                entity -> !isImmobile(),
                                entity -> !entity.getNbt().getBoolean("IsImmuneToZombification")
                        ), 12, 1),
                        new Behavior(new PlaySoundExecutor(Sound.MOB_PIGLIN_ANGRY, 0.8f, 1.2f, 0.8f, 0.8f), all(new RandomSoundEvaluator(), entity -> isAngry()), 10, 1),
                        new Behavior(new PlaySoundExecutor(Sound.MOB_PIGLIN_AMBIENT, 0.8f, 1.2f, 0.8f, 0.8f), all(new RandomSoundEvaluator(), entity -> !isAngry()), 9, 1),
                        new Behavior(new EntityPiglin.PiglinMeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.5f, 40, true, 30),new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 6, 1),
                        new Behavior(new EntityPiglin.PiglinMeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.5f, 40, false, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 5, 1),
                        new Behavior(new EntityPiglin.PiglinFleeFromTargetExecutor(CoreMemoryTypes.NEAREST_SHARED_ENTITY), all(
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.NEAREST_SHARED_ENTITY),
                                entity -> !isBaby()
                        ), 3, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(new NearestPlayerSensor(40, 0, 20),
                        new NearestTargetEntitySensor<>(0, 16, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget),
                        new NearestPlayerAngryPiglinSensor(),
                        new NearestEntitySensor(EntityZombiePigman.class, CoreMemoryTypes.NEAREST_SHARED_ENTITY, 8 , 0),
                        new BlockSensor(BlockDoor.class, CoreMemoryTypes.NEAREST_BLOCK, 2, 2, 20)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.diffHandDamage = new float[]{6f, 10f, 15f};
        setItemInHand(Item.get(Item.GOLDEN_AXE));
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return switch (entity.getIdentifier()) {
            case Entity.WITHER_SKELETON, Entity.WITHER -> true;
            default -> false;
        };
    }

    @Override
    public String getOriginalName() {
        return "Piglin Brute";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("piglin", "adult_piglin", "piglin_brute", "monster");
    }
}
