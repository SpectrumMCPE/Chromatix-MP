package chromatix.entity.mob;

import chromatix.entity.Entity;
import chromatix.entity.EntityFlyable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LiftController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.SpaceMoveController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.GhastShootExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.executor.SpaceRandomRoamExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleSpaceAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.FlyingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.projectile.EntityFireball;
import chromatix.item.Item;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntityGhast extends EntityMob implements EntityFlyable {

    @Override
    @NotNull public String getIdentifier() {
        return GHAST;
    }

    public EntityGhast(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .coreBehaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_GHAST_MOAN), new RandomSoundEvaluator(), 2, 1),
                        new Behavior(new SpaceRandomRoamExecutor(0.15f, 12, 100, 200, false, -1, true, 10), none(), 1, 1)
                )
                .behaviors(
                        new Behavior(new GhastShootExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 64, true, 60, 10), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 2, 1),
                        new Behavior(new GhastShootExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 28, true, 60, 10), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 1, 1)
                )
                .sensors(new NearestPlayerSensor(64, 0, 20))
                .controllers(new SpaceMoveController(), new LookController(true, true), new LiftController())
                .routeFinder(new SimpleSpaceAStarRouteFinder(new FlyingPosEvaluator(), this))
                .build();
    }

    @Override
    public void kill() {
        Arrays.stream(getLevel().getEntities()).filter(entity -> {
            if(entity instanceof EntityFireball fireball) {
                return fireball.shootingEntity == this && !fireball.closed;
            }
            return false;
        }).forEach(Entity::close);
        super.kill();
    }

    @Override
    public float getWidth() {
        return 4;
    }

    @Override
    public float getHeight() {
        return 4;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(10);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.3f);
    }

    @Override
    public String getOriginalName() {
        return "Ghast";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("ghast", "monster", "mob");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        List<Item> drops = new ArrayList<>();

        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);

        if (Utils.rand(0, 1) == 1) {
            int amount = Utils.rand(0, 1 + looting);
            if (amount > 0) {
                drops.add(Item.get(Item.GHAST_TEAR, 0, amount));
            }
        }

        if (Utils.rand(0, 2) != 0) {
            int amount = Utils.rand(0, 2 + looting);
            if (amount > 0) {
                drops.add(Item.get(Item.GUNPOWDER, 0, amount));
            }
        }

        return drops.toArray(Item.EMPTY_ARRAY);
    }

}
