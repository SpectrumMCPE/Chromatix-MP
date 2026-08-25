package chromatix.entity.passive;

import chromatix.entity.EntityFlyable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LiftController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.SpaceMoveController;
import chromatix.entity.ai.executor.SpaceRandomRoamExecutor;
import chromatix.entity.ai.route.finder.impl.SimpleSpaceAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.FlyingPosEvaluator;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntityBat extends EntityAnimal implements EntityFlyable {
    @Override
    @NotNull public String getIdentifier() {
        return BAT;
    }

    public EntityBat(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new SpaceRandomRoamExecutor(0.3f, 12, 100, 20, false, -1, true, 10), (entity -> true), 1, 1)
                )
                .controllers(new SpaceMoveController(), new LookController(true, true), new LiftController())
                .routeFinder(new SimpleSpaceAStarRouteFinder(new FlyingPosEvaluator(), this))
                .build();
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
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
        return "Bat";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("bat", "mob");
    }

    @Override
    public Integer getExperienceDrops() {
        return 0;
    }
}
