package chromatix.entity.passive;

import chromatix.block.BlockID;
import chromatix.entity.EntityID;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.FluctuateController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.PassByTimeEvaluator;
import chromatix.entity.ai.evaluator.ProbabilityEvaluator;
import chromatix.entity.ai.executor.AnimalGrowExecutor;
import chromatix.entity.ai.executor.BreedingExecutor;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.LookAtTargetExecutor;
import chromatix.entity.ai.executor.LoveTimeoutExecutor;
import chromatix.entity.ai.executor.TemptExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.AgeableComponent;
import chromatix.entity.components.BreedableComponent;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class EntityGoat extends EntityAnimal implements EntityWalkable {

    @Override
    @NotNull public String getIdentifier() {
        return GOAT;
    }

    public EntityGoat(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public float getHeight() {
        return 1.3f;
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(10);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.4f);
    }

    @Override
    public @Nullable BreedableComponent getComponentBreedable() {
        return new BreedableComponent(
                Set.of(
                    BlockID.WHEAT
                ),
                List.of(
                    new BreedableComponent.BreedsWith(EntityID.GOAT, EntityID.GOAT)
                ),
                false
        );
    }

    @Override
    public AgeableComponent getComponentAgeable() {
        return new AgeableComponent(
                null,
                1200f,
                List.of(
                    new AgeableComponent.FeedItem(BlockID.WHEAT)
                ),
                null,
                null,
                null
        );
    }

    @Override
    public String getOriginalName() {
        return "Goat";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("goat", "animal");
    }

    private static final Set<String> TEMPT_ITEMS = Set.of(
        BlockID.WHEAT
    );

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .coreBehaviors(
                    new Behavior(
                        new LoveTimeoutExecutor(20 * 30),
                            e -> e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE),
                        2, 1
                    ),
                    new Behavior(
                        new AnimalGrowExecutor(),
                            all(
                                e -> e.isAgeable(),
                                e -> e.isBaby(),
                                e -> !e.isGrowthPaused(),
                                e -> e.getTicksGrowLeft() > 0
                            ),
                        1, 1, 1200
                    )
                )
                .behaviors(
                    new Behavior(
                        new FlatRandomRoamExecutor(0.4f, 12, 40, true, 100, true, 10),
                            new PassByTimeEvaluator(CoreMemoryTypes.LAST_BE_ATTACKED_TIME, 0, 100),
                        4, 1
                    ),
                    new Behavior(
                        new BreedingExecutor(16, 200, 0.25f),
                            all(
                                e -> !e.isBaby(),
                                e -> e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE)
                            ),
                        3, 1
                    ),
                    new Behavior(
                        new TemptExecutor(0.75f, TEMPT_ITEMS),
                            all(
                                e -> !e.getMemoryStorage().get(CoreMemoryTypes.IS_IN_LOVE),
                                e -> TemptExecutor.hasTemptingPlayer(e, false, 10, TEMPT_ITEMS)
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
                    new WalkController(),
                    new LookController(true, true),
                    new FluctuateController()
                )
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

}
