package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.MemoryCheckNotEmptyEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.executor.PotionThrowExecutor;
import chromatix.entity.ai.executor.UsePotionExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestEntitySensor;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.item.Item;
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
public class EntityWitch extends EntityMob implements EntityWalkable {

    @Override
    @NotNull public String getIdentifier() {
        return WITCH;
    }

    public EntityWitch(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .coreBehaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_WITCH_AMBIENT), new RandomSoundEvaluator(), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .behaviors(
                        new Behavior(new UsePotionExecutor(0.3f, 30, 20), all(
                                new MemoryCheckNotEmptyEvaluator(CoreMemoryTypes.LAST_BE_ATTACKED_TIME),
                                entity -> entity.getLevel().getTick() - getMemoryStorage().get(CoreMemoryTypes.LAST_BE_ATTACKED_TIME) <= 1
                        ), 4, 1, 1, false),
                        new Behavior(new PotionThrowExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 15, true, 30, 20), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 3, 1),
                        new Behavior(new PotionThrowExecutor(CoreMemoryTypes.NEAREST_GOLEM, 0.3f, 15, true, 30, 20), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_GOLEM), 2, 1),
                        new Behavior(new PotionThrowExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 15, true, 30, 20), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 1, 1)
                )
                .sensors(
                        new NearestPlayerSensor(16, 0, 20),
                        new NearestEntitySensor(EntityGolem.class, CoreMemoryTypes.NEAREST_GOLEM, 42, 0)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
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
        return HealthComponent.value(26);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.25f);
    }

    @Override
    public String getOriginalName() {
        return "Witch";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("witch", "monster", "mob");
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        List<Item> drops = new ArrayList<>();

        drops.add(Item.get(
                Item.REDSTONE,
                0,
                Utils.rand(4, 8 + looting)
        ));

        int max = 6 + (looting * 3);

        record ExtraDrop(String id, int chance) {}

        ExtraDrop[] extras = {
                new ExtraDrop(Item.STICK, 3349),
                new ExtraDrop(Item.SPIDER_EYE, 1787),
                new ExtraDrop(Item.GLOWSTONE_DUST, 1787),
                new ExtraDrop(Item.GUNPOWDER, 1787),
                new ExtraDrop(Item.SUGAR, 1787),
                new ExtraDrop(Item.GLASS_BOTTLE, 1787)
        };

        for (ExtraDrop drop : extras) {
            if (Utils.rand(0, 9999) < drop.chance()) {
                drops.add(Item.get(drop.id(), 0, Utils.rand(0, max)));
            }
        }

        return drops.toArray(Item.EMPTY_ARRAY);
    }
}
