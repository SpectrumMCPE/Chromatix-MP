package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.EntitySmite;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.BowShootExecutor;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestEntitySensor;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntityStray extends EntityMob implements EntityWalkable, EntitySmite {

    @Override
    @NotNull public String getIdentifier() {
        return STRAY;
    }

    public EntityStray(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        if (getItemInHand().isNull()) {
            setItemInHand(Item.get(ItemID.BOW));
        }
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(20);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.25f);
    }

    @Override
    public String getOriginalName() {
        return "Stray";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("stray", "skeleton", "monster", "mob", "undead");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        List<Item> drops = new ArrayList<>();

        float boneChance = 0.66f + (0.12f * looting);
        boneChance = Math.min(boneChance, 1.0f);

        if (Utils.rand(0f, 1f) < boneChance) {
            int amount = Utils.rand(1, 2 + looting);
            drops.add(Item.get(Item.BONE, 0, amount));
        }

        float arrowChance = 0.55f + (0.10f * looting);
        arrowChance = Math.min(arrowChance, 1.0f);

        if (Utils.rand(0f, 1f) < arrowChance) {
            int amount = Utils.rand(1, 2 + looting);
            drops.add(Item.get(Item.ARROW, 0, amount));
        }

        return drops.toArray(Item.EMPTY_ARRAY);
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

    @Override
    protected IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_SKELETON_SAY), new RandomSoundEvaluator(), 5, 1),
                        new Behavior(new BowShootExecutor(this::getItemInHand, CoreMemoryTypes.ATTACK_TARGET, 0.3f, 15, true, 30, 20), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 4, 1),
                        new Behavior(new BowShootExecutor(this::getItemInHand, CoreMemoryTypes.NEAREST_GOLEM, 0.3f, 15, true, 30, 20), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_GOLEM), 3, 1),
                        new Behavior(new BowShootExecutor(this::getItemInHand, CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 15, true, 30, 20), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(
                        new NearestPlayerSensor(16, 0, 20),
                        new NearestEntitySensor(EntityGolem.class, CoreMemoryTypes.NEAREST_GOLEM, 42, 0)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

}
