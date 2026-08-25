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
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

import java.util.Set;

/**
 * Base class for all fish.
 */
public abstract class EntityFish extends EntityAnimal implements EntitySwimmable {

    public EntityFish(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    //removing the stranded sound effect feels off
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
}
