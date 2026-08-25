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
import chromatix.entity.weather.EntityLightningBolt;
import chromatix.event.entity.EntityDamageByEntityEvent;
import chromatix.event.entity.EntityDamageEvent;
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
public class EntitySkeleton extends EntityMob implements EntityWalkable, EntitySmite {
    @Override
    @NotNull
    public String getIdentifier() {
        return SKELETON;
    }


    public EntitySkeleton(IChunk chunk, CompoundTag nbt) {
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
        return MovementComponent.value(0.35f);
    }

    @Override
    public String getOriginalName() {
        return "Skeleton";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("skeleton", "undead", "monster", "mob");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);
        List<Item> drops = new ArrayList<>();

        int bones = Utils.rand(0, 2 + looting);
        if (bones > 0) {
            drops.add(Item.get(Item.BONE, 0, bones));
        }

        int arrows = Utils.rand(0, 2 + looting);
        if (arrows > 0) {
            drops.add(Item.get(Item.ARROW, 0, arrows));
        }

        for (Item equipped : this.getEquipmentInventory().getContents().values()) {
            if (!equipped.isNull() && !equipped.hasEnchantment(Enchantment.ID_VANISHING_CURSE)) {
                if (equipped.isBow()) {
                    int chance = 85 + looting * 10;
                    if (Utils.rand(0, 999) < chance) {
                        Item drop = equipped.clone();
                        drop.setDamage(Utils.rand(193, 384));
                        drops.add(drop);
                    }
                } else {
                    drops.add(equipped.clone());
                }
            }
        }

        for (Item armor : this.getArmorInventory().getContents().values()) {
            if (!armor.isNull() && !armor.hasEnchantment(Enchantment.ID_VANISHING_CURSE)) {
                drops.add(armor.clone());
            }
        }

        return drops.toArray(Item.EMPTY_ARRAY);
    }

    @Override
    public double getFloatingForceFactor() {
        return 0.7;
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
        if (!(this instanceof EntityParched)) {
            burn(this);
        }
        return super.onUpdate(currentTick);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if ((source instanceof EntityDamageByEntityEvent ev) && ev.getDamager() instanceof EntityLightningBolt) {
            ev.setCancelled(true);
            return false;
        }
        return super.attack(source);
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
