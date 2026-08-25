package chromatix.entity.passive;

import chromatix.entity.EntityID;
import chromatix.entity.EntityVariant;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.FluctuateController;
import chromatix.entity.ai.controller.HoppingController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.evaluator.PassByTimeEvaluator;
import chromatix.entity.ai.evaluator.ProbabilityEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.LookAtTargetExecutor;
import chromatix.entity.ai.executor.LoveTimeoutExecutor;
import chromatix.entity.ai.executor.TemptExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.BreedableComponent;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.item.ItemID;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

// TODO: Pregnant logic / Behavior
public class EntityFrog extends EntityAnimal implements EntityWalkable, EntityVariant {
    @Override
    @NotNull public String getIdentifier() {
        return FROG;
    }

    public EntityFrog(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getHeight() {
        return 0.55f;
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(10);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.1f);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        if (!hasVariant()) {
            this.setVariant(randomVariant());
        }
    }

    @Override
    public String getOriginalName() {
        return "Frog";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("frog", "mob");
    }

    @Override
    public @Nullable BreedableComponent getComponentBreedable() {
        return new BreedableComponent(
                null,
                null,
                null,
                null,
                Set.of(
                    ItemID.SLIME_BALL
                ),
                List.of(
                    new BreedableComponent.BreedsWith(EntityID.FROG, EntityID.TADPOLE)
                ),
                true,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                false,
                null
        );
    }

    @Override
    public int[] getAllVariant() {
        return new int[] {0,1,2};
    }

    private static final Set<String> TEMPT_ITEMS = Set.of(
        ItemID.SLIME_BALL
    );

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .coreBehaviors(
                    new Behavior(
                        new LoveTimeoutExecutor(20 * 30),
                            e -> e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE),
                        3, 1
                    )
                )
                .behaviors(
                    new Behavior(
                        new FlatRandomRoamExecutor(0.4f, 12, 40, true, 100, true, 10),
                            new PassByTimeEvaluator(CoreMemoryTypes.LAST_BE_ATTACKED_TIME, 0, 100),
                        4, 1
                    ),
                    // TODO: Pregnant logic
                    new Behavior(
                        new TemptExecutor(1.25f, true, false, false, 10, 2.0f, null, TEMPT_ITEMS),
                            all(
                                e -> !e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE),
                                e -> TemptExecutor.hasTemptingPlayer(e, true, 10, TEMPT_ITEMS)
                            ),
                        2, 1
                    ),
                    new Behavior(
                        new LookAtTargetExecutor(CoreMemoryTypes.NEAREST_PLAYER, 100),
                            new ProbabilityEvaluator(4, 10),
                        1, 1, 100
                    ),
                    new Behavior(
                        new FlatRandomRoamExecutor(0.2f, 12, 100, false, -1, true, 10),
                            (entity -> true),
                        1, 1
                    )
                )
                .sensors(
                    new NearestPlayerSensor(8, 0, 20)
                )
                .controllers(
                    new HoppingController(5),
                    new LookController(true, true),
                    new FluctuateController()
                )
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

}
