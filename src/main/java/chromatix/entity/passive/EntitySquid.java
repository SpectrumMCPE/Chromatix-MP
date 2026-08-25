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
import chromatix.item.Item;
import chromatix.item.ItemID;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntitySquid extends EntityAnimal implements EntitySwimmable {
    @Override
    @NotNull public String getIdentifier() {
        return SQUID;
    }

    public EntitySquid(IChunk chunk, CompoundTag nbt) {
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
    public float getWidth() {
        return 0.95f;
    }

    @Override
    public float getHeight() {
        return 0.95f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(10);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.2f);
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        int amount = Utils.rand(1, 3 + looting);

        return new Item[]{
                Item.get(ItemID.INK_SAC, 0, amount)
        };
    }

    @Override
    public String getOriginalName() {
        return "Squid";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("squid", "mob");
    }

    @Override
    public boolean isEnablePitch() {
        return true;
    }
}
