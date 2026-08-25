package chromatix.entity.mob;

import chromatix.entity.Entity;
import chromatix.entity.EntityVariant;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.HoppingController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.MeleeAttackExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestTargetEntitySensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.passive.EntityFrog;
import chromatix.event.entity.EntityDamageByEntityEvent;
import chromatix.item.Item;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * @author PikyCZ
 */
public class EntitySlime extends EntityMob implements EntityWalkable, EntityVariant {

    @Override
    @NotNull
    public String getIdentifier() {
        return SLIME;
    }

    private static final String TAG_SLIME_SIZE = "SlimeSize";
    public static final int SIZE_SMALL = 1;
    public static final int SIZE_MEDIUM = 2;
    public static final int SIZE_BIG = 4;

    public EntitySlime(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getVariant() {
        if (getBehaviorGroup() != null) {
            Integer variant = getMemoryStorage().get(CoreMemoryTypes.VARIANT);
            if (variant != null) return variant;
        }

        if (this.nbt.contains(TAG_SLIME_SIZE)) {
            return this.getNbt().getInt(TAG_SLIME_SIZE);
        }

        return SIZE_BIG;
    }

    @Override
    public void setVariant(int variant) {
        this.nbt.putInt(TAG_SLIME_SIZE, variant);

        if (getBehaviorGroup() != null) {
            getMemoryStorage().put(CoreMemoryTypes.VARIANT, variant);
        }
    }

    @Override
    public boolean hasVariant() {
        if (getBehaviorGroup() != null && getMemoryStorage().notEmpty(CoreMemoryTypes.VARIANT)) {
            return true;
        }

        return this.nbt.contains(TAG_SLIME_SIZE);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.3f, 40, false, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(new NearestTargetEntitySensor<>(0, 16, 20,
                        List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget))
                .controllers(new HoppingController(40), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    protected void initEntity() {
        if (!this.nbt.contains(TAG_SLIME_SIZE)) {
            this.nbt.putInt(TAG_SLIME_SIZE, randomVariant());
        }

        super.initEntity();

        if (getBehaviorGroup() != null) {
            getMemoryStorage().put(CoreMemoryTypes.VARIANT, this.getNbt().getInt(TAG_SLIME_SIZE));
        }

        if (getVariant() == SIZE_BIG) {
            this.diffHandDamage = new float[]{3, 4, 6};
        } else if (getVariant() == SIZE_MEDIUM) {
            this.diffHandDamage = new float[]{2, 2, 3};
        } else {
            this.diffHandDamage = new float[]{0, 0, 0};
        }

        recalculateBoundingBox();
    }

    @Override
    public double getFloatingForceFactor() {
        return 0;
    }

    @Override
    public float getWidth() {
        if (getBehaviorGroup() == null) return 0;
        return 0.51f + getVariant() * 0.51f;
    }

    @Override
    public float getHeight() {
        if (getBehaviorGroup() == null) return 0;
        return 0.51f + getVariant() * 0.51f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        if (!hasVariant()) this.setVariant(randomVariant());
        int variantHealth = switch (getVariant()) {
            case SIZE_BIG -> 16;
            case SIZE_MEDIUM -> 4;
            case SIZE_SMALL -> 1;
            default -> 16;
        };

        return HealthComponent.value(variantHealth);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        if (!hasVariant()) this.setVariant(randomVariant());
        float variantMovement = switch (getVariant()) {
            case SIZE_BIG -> 0.6f;
            case SIZE_MEDIUM -> 0.4f;
            case SIZE_SMALL -> 0.3f;
            default -> 0.6f;
        };
        return MovementComponent.value(variantMovement);
    }

    @Override
    public String getOriginalName() {
        return "Slime";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("slime", "monster", "mob");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        if (getVariant() != SIZE_SMALL) {
            return Item.EMPTY_ARRAY;
        }

        if (getLastDamageCause() instanceof EntityDamageByEntityEvent event
                && event.getDamager() instanceof EntityFrog) {
            return new Item[]{Item.get(Item.SLIME_BALL, 0, 1)};
        }

        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        int amount = Utils.rand(1, 2) + looting;

        return new Item[]{
                Item.get(Item.SLIME_BALL, 0, amount)
        };
    }

    @Override
    public Integer getExperienceDrops() {
        return getVariant();
    }

    @Override
    public int[] getAllVariant() {
        return new int[]{1, 2, 4};
    }

    private int getSmaller() {
        return switch (getVariant()) {
            case 4 -> 2;
            default -> getVariant() - 1;
        };
    }

    @Override
    public void kill() {
        if (!this.justCreated && getVariant() != SIZE_SMALL) {
            final int smaller = getSmaller();
            for (int i = 1; i < Utils.rand(2, 5); i++) {
                CompoundTag childNbt = Entity.getDefaultNBT(
                    this.add(Utils.rand(-0.5, 0.5), 0, Utils.rand(-0.5, 0.5)));
                childNbt.putInt(TAG_SLIME_SIZE, smaller);

                EntitySlime slime = new EntitySlime(this.getChunk(), childNbt);
                slime.setRotation(this.yaw, this.pitch);
                slime.setVariant(smaller);
                slime.spawnToAll();
            }
        }
        super.kill();
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return super.attackTarget(entity) || entity instanceof EntityGolem;
    }
}
