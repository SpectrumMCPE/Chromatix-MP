package chromatix.entity.passive;

import chromatix.entity.EntitySwimmable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.DiveController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.SpaceMoveController;
import chromatix.entity.ai.executor.SpaceRandomRoamExecutor;
import chromatix.entity.ai.route.finder.impl.SimpleSpaceAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.SwimmingPosEvaluator;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class EntityTadpole extends EntityAnimal implements EntitySwimmable {

    @Override
    @NotNull public String getIdentifier() {
        return TADPOLE;
    }
    public EntityTadpole(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(
                                new SpaceRandomRoamExecutor(0.36f, 12, 1, 80, false, -1, false, 10),
                                entity -> true, 1)
                )
                .controllers(new SpaceMoveController(), new LookController(true, true), new DiveController())
                .routeFinder(new SimpleSpaceAStarRouteFinder(new SwimmingPosEvaluator(), this))
                .build();
    }

    @Override
    public float getHeight() {
        return 0.8f;
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(6);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.1f);
    }

    @Override
    public String getOriginalName() {
        return "Tadpole";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("tadpole", "mob");
    }
}
