package chromatix.entity.mob;

import chromatix.Player;
import chromatix.block.BlockTurtleEgg;
import chromatix.entity.Entity;
import chromatix.entity.EntitySmite;
import chromatix.entity.EntitySwimmable;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.DistanceEvaluator;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.evaluator.SightEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.JumpExecutor;
import chromatix.entity.ai.executor.MeleeAttackExecutor;
import chromatix.entity.ai.executor.MoveToTargetExecutor;
import chromatix.entity.ai.executor.NearestBlockIncementExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.executor.TridentThrowExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.AttackSensor;
import chromatix.entity.ai.sensor.BlockSensor;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.ai.sensor.NearestTargetEntitySensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.passive.EntityAxolotl;
import chromatix.item.Item;
import chromatix.item.ItemTrident;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class EntityDrowned extends EntityZombie implements EntitySwimmable, EntityWalkable, EntitySmite {

    @Override
    @NotNull
    public String getIdentifier() {
        return DROWNED;
    }

    public EntityDrowned(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(20);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .coreBehaviors(
                        new Behavior(new NearestBlockIncementExecutor(), entity -> !getMemoryStorage().isEmpty(CoreMemoryTypes.NEAREST_BLOCK) && getMemoryStorage().get(CoreMemoryTypes.NEAREST_BLOCK) instanceof BlockTurtleEgg, 1, 1)
                )
                .behaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_DROWNED_SAY_WATER), all(new RandomSoundEvaluator(), entity -> isInsideOfWater()), 11, 1),
                        new Behavior(new PlaySoundExecutor(Sound.MOB_DROWNED_SAY), all(new RandomSoundEvaluator(), entity -> !isInsideOfWater()), 10, 1),
                        new Behavior(new JumpExecutor(), all(entity -> !getMemoryStorage().isEmpty(CoreMemoryTypes.NEAREST_BLOCK), entity -> entity.getCollisionBlocks().stream().anyMatch(block -> block instanceof BlockTurtleEgg)), 9, 1, 10),
                        new Behavior(new MoveToTargetExecutor(CoreMemoryTypes.NEAREST_BLOCK, 0.3f, true), new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.NEAREST_BLOCK), 8, 1),
                        new Behavior(new TridentThrowExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 15, true, 30, 20), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                new DistanceEvaluator(CoreMemoryTypes.ATTACK_TARGET, 32, 3),
                            entity -> getItemInHand().getId().equals(Item.TRIDENT),
                                new SightEvaluator(CoreMemoryTypes.ATTACK_TARGET)
                        ), 7, 1),
                        new Behavior(new TridentThrowExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 15, false, 30, 20), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                new DistanceEvaluator(CoreMemoryTypes.NEAREST_PLAYER, 32, 3),
                                entity -> getItemInHand().getId().equals(Item.TRIDENT),
                                any(
                                        entity -> getLevel().isNight(),
                                        entity -> getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER) != null && getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER).isInsideOfWater()
                                ),
                                new SightEvaluator(CoreMemoryTypes.NEAREST_PLAYER)
                        ), 6, 1),
                        new Behavior(new TridentThrowExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.3f, 15, false, 30, 20), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new DistanceEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 32, 3),
                                entity -> getItemInHand().getId().equals(Item.TRIDENT),
                                new SightEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET)
                        ), 5, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 4, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, false, 30), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                any(
                                        entity -> getLevel().isNight(),
                                        entity -> getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER) != null && getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER).isInsideOfWater()
                                )
                        ), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, false, 10), none(), 1, 1)
                )
                .sensors(
                        new AttackSensor(CoreMemoryTypes.ATTACK_TARGET),
                        new NearestPlayerSensor(64, 0, 0),
                        new NearestTargetEntitySensor<>(0, 16, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget),
                        new BlockSensor(BlockTurtleEgg.class, CoreMemoryTypes.NEAREST_BLOCK, 11, 15, 10)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    public double getFloatingForceFactor() {
        if (any(
                new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.NEAREST_BLOCK)
        ).evaluate(this)) {
            if (hasWaterAt(this.getFloatingHeight())) {
                return 1.3;
            }
            return 0.7;
        }
        return 0;
    }

    @Override
    protected void initEntity() {
        this.diffHandDamage = new float[]{2.5f, 3f, 4.5f};
        super.initEntity();
        getMemoryStorage().put(CoreMemoryTypes.ENABLE_DIVE_FORCE, true);
        int random = Utils.rand(0, 10000);
        if (random < 85) {
            setItemInHand(Item.get(Item.FISHING_ROD));
        } else if (random < 1585 && !wasTransformed()) {
            setItemInHand(Item.get(Item.TRIDENT));
        }
        if (Utils.rand(0, 100) < 3) {
            setItemInOffhand(Item.get(Item.NAUTILUS_SHELL));
        }
    }

    @Override
    public String getOriginalName() {
        return "Drowned";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("drowned", "zombie", "undead", "monster", "mob");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        Item trident = Item.AIR;
        if (getItemInHand() instanceof ItemTrident) {
            int lootingLevel = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);

            if (Utils.rand(0, 100) < Math.min(37, 25 + lootingLevel)) {
                trident = Item.get(Item.TRIDENT);
            }
        }
        return new Item[]{
                Item.get(Item.ROTTEN_FLESH),
                trident
        };
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
    public Integer getExperienceDrops() {
        return isBaby() ? 7 : 5;
    }

    public boolean wasTransformed() {
        if (this.nbt.contains("Transformed")) {
            return this.getNbt().getBoolean("Transformed");
        }
        return false;
    }

    @Override
    public boolean attackTarget(Entity entity) {
        return super.attackTarget(entity) || entity instanceof EntityAxolotl;
    }

    @Override
    protected boolean transform() {
        throw new UnsupportedOperationException("Drowned cannot transform");
    }
}
