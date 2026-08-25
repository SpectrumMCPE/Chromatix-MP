package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.MeleeAttackExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.ai.sensor.NearestTargetEntitySensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.item.Item;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class EntityZoglin extends EntityMob implements EntityWalkable {

    @Override
    @NotNull public String getIdentifier() {
        return ZOGLIN;
    }

    public EntityZoglin(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 2, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(
                        new NearestPlayerSensor(40, 0, 20),
                        new NearestTargetEntitySensor<>(0, 16, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    protected void initEntity() {
        super.initEntity();
        this.diffHandDamage = new float[]{1f, 1f, 1f};
    }

    @Override
    public float getBabyScale() {
        // baby zoglin is 0.85 wide against the adult's 1.4
        return 0.6071f;
    }

    @Override
    public float getWidth() {
        return 1.4f;
    }

    @Override
    public float getHeight() {
        return 1.4f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(40);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.25f);
    }

    @Override
    public float[] getDiffHandDamage() {
        if(isBaby()) {
            return super.getDiffHandDamage();
        } else return new float[] {
                Utils.rand(2.5f, 5f),
                Utils.rand(3f, 8f),
                Utils.rand(4.5f, 12f),
        };
    }

    @Override
    public String getOriginalName() {
        return "Zoglin";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("zoglin", "zoglin_baby", "undead", "monster", "mob");
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
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        int amount = Utils.rand(1, 3 + looting);

        return new Item[]{
                Item.get(Item.ROTTEN_FLESH, 0, amount)
        };
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return (!(entity instanceof EntityZoglin) && entity instanceof EntityIntelligent);
    }

    @Override
    public Integer getExperienceDrops() {
        return isBaby() ? 0 : Utils.rand(1,3);
    }
}
