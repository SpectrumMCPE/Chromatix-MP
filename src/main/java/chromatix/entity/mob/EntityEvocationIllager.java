package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.entity.EntityID;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.DistanceEvaluator;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.MemoryCheckEmptyEvaluator;
import chromatix.entity.ai.evaluator.PassByTimeEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.DoNothingExecutor;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.FleeFromTargetExecutor;
import chromatix.entity.ai.executor.LookAtTargetExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.executor.evocation.ColorConversionExecutor;
import chromatix.entity.ai.executor.evocation.FangCircleExecutor;
import chromatix.entity.ai.executor.evocation.FangLineExecutor;
import chromatix.entity.ai.executor.evocation.VexSummonExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestTargetEntitySensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.passive.EntitySheep;
import chromatix.item.Item;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.GameRule;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.DyeColor;
import chromatix.utils.Utils;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static chromatix.entity.ai.memory.CoreMemoryTypes.LAST_MAGIC;

    /**
     * @author PikyCZ
     */
public class EntityEvocationIllager extends EntityIllager implements EntityWalkable {
    public EntityEvocationIllager(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    @NotNull public String getIdentifier() {
        return EntityID.EVOCATION_ILLAGER;
    }

    @Override
    protected IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_EVOCATION_ILLAGER_AMBIENT), new RandomSoundEvaluator(), 10, 1),
                        new Behavior(new FleeFromTargetExecutor(CoreMemoryTypes.NEAREST_SHARED_ENTITY, 0.5f, true, 9), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SHARED_ENTITY),
                                new DistanceEvaluator(CoreMemoryTypes.NEAREST_SHARED_ENTITY, 8),
                                any(
                                        new MemoryCheckEmptyEvaluator(LAST_MAGIC),
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.NONE
                                )
                        ), 9, 1),
                        new Behavior(new FleeFromTargetExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 0.5f, true, 11), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new DistanceEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 10),
                                any(
                                        new MemoryCheckEmptyEvaluator(LAST_MAGIC),
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.NONE
                                )
                        ), 8, 1),
                        new Behavior(new ColorConversionExecutor(), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_CONVERSION, 100),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 40),
                                entity -> {
                                    for(Entity entity1 : entity.getLevel().getNearbyEntities(entity.getBoundingBox().grow(16, 16, 16))) {
                                        if(entity1 instanceof EntitySheep entitySheep) {
                                            if(entitySheep.getColor() == DyeColor.BLUE.getWoolData()) {
                                                return true;
                                            }
                                        }
                                    }
                                    return false;
                                },
                                any(
                                        new MemoryCheckEmptyEvaluator(LAST_MAGIC),
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.NONE,
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.COLOR_CONVERSION
                                ),
                                entity -> entity.getLevel().getGameRules().getBoolean(GameRule.MOB_GRIEFING)
                        ), 7, 1),
                        new Behavior(new VexSummonExecutor(), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_SUMMON, 340),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 40),
                                entity -> {
                                    int count = 0;
                                    for(Entity entity1 : entity.getLevel().getNearbyEntities(entity.getBoundingBox().grow(15, 15, 15))) {
                                        if(entity1 instanceof EntityVex) count++;
                                    }
                                    return count < 8;
                                },
                                any(
                                        new MemoryCheckEmptyEvaluator(LAST_MAGIC),
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.NONE,
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.SUMMON
                                )
                        ), 6, 1),
                        new Behavior(new FangCircleExecutor(), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_CAST, 100),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 40),
                                new DistanceEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 3),
                                any(
                                        new MemoryCheckEmptyEvaluator(LAST_MAGIC),
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.NONE,
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.CAST_CIRLCE
                                )
                        ), 5, 1),
                        new Behavior(new FangLineExecutor(), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_CAST, 100),
                                new PassByTimeEvaluator(CoreMemoryTypes.LAST_ATTACK_TIME, 40),
                                any(
                                        new MemoryCheckEmptyEvaluator(LAST_MAGIC),
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.NONE,
                                        entity -> entity.getMemoryStorage().get(LAST_MAGIC) == SPELL.CAST_LINE
                                )
                                ), 4, 1),
                        new Behavior(new LookAtTargetExecutor(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET, 1), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET),
                                entity -> !entity.getDataFlag(ActorFlags.CASTING)
                        ), 3, 1),
                        new Behavior(new DoNothingExecutor(), entity -> entity.getDataFlag(ActorFlags.CASTING), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(
                        new NearestTargetEntitySensor<>(0, 16, 20,
                                List.of(CoreMemoryTypes.NEAREST_SUITABLE_ATTACK_TARGET), this::attackTarget),
                        new NearestTargetEntitySensor<>(0, 16, 20,
                                List.of(CoreMemoryTypes.NEAREST_SHARED_ENTITY), entity -> entity instanceof EntityCreaking)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);
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
        return HealthComponent.value(24);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.5f);
    }

    @Override
    public String getOriginalName() {
        return "Evoker";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("evocation_illager", "monster", "illager", "mob");
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public Integer getExperienceDrops() {
        return 10;
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int lootingLevel = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        return new Item[] {
                Item.get(Item.TOTEM_OF_UNDYING),
                Item.get(Item.EMERALD, 0, Utils.rand(0, 2 + lootingLevel))
        };
    }


    public enum SPELL {
        NONE,
        CAST_LINE,
        CAST_CIRLCE,
        SUMMON,
        COLOR_CONVERSION
    }

}
