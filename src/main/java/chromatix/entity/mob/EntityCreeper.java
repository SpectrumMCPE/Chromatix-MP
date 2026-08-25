package chromatix.entity.mob;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.entity.EntityInteractable;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.FluctuateController;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.executor.EntityExplosionExecutor;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.FleeFromTargetExecutor;
import chromatix.entity.ai.executor.MoveToTargetExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestEntitySensor;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.passive.EntityCat;
import chromatix.entity.passive.EntityOcelot;
import chromatix.entity.weather.EntityLightningStrike;
import chromatix.event.entity.CreeperPowerEvent;
import chromatix.event.entity.EntityDamageByEntityEvent;
import chromatix.item.Item;
import chromatix.item.enchantment.Enchantment;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.math.Vector3;
import chromatix.nbt.tag.CompoundTag;
import chromatix.utils.Utils;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorDataTypes;
import org.cloudburstmc.protocol.bedrock.data.actor.ActorFlags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


/**
 * @author Box.
 */
public class EntityCreeper extends EntityMob implements EntityWalkable, EntityInteractable {
    @Override
    @NotNull
    public String getIdentifier() {
        return CREEPER;
    }

    public EntityCreeper(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new FleeFromTargetExecutor(CoreMemoryTypes.NEAREST_SHARED_ENTITY, 0.3f, true, 4), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SHARED_ENTITY), 5, 1),
                        new Behavior(
                                new EntityExplosionExecutor(30, 3, CoreMemoryTypes.SHOULD_EXPLODE),
                                all(
                                        entity -> entity.getMemoryStorage().compareDataTo(CoreMemoryTypes.SHOULD_EXPLODE, true),
                                        any(
                                                entity -> !entity.getMemoryStorage().get(CoreMemoryTypes.EXPLODE_CANCELLABLE),
                                                all(
                                                        new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                                        entity -> hasClearLineOfSight(entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER))
                                                ),
                                                all(
                                                        new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                                        entity -> hasClearLineOfSight(entity.getMemoryStorage().get(CoreMemoryTypes.ATTACK_TARGET))
                                                )
                                        )
                                ), 4, 1
                        ),
                        new Behavior(new MoveToTargetExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, true, 16f, 3f, true), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET),
                                entity -> !entity.getMemoryStorage().notEmpty(CoreMemoryTypes.ATTACK_TARGET) || !(entity.getMemoryStorage().get(CoreMemoryTypes.ATTACK_TARGET) instanceof Player player) || player.isSurvival()
                        ), 3, 1),
                        new Behavior(new MoveToTargetExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, true, 16f, 3f), all(
                                new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER),
                                entity -> {
                                    if (entity.getMemoryStorage().isEmpty(CoreMemoryTypes.NEAREST_PLAYER)) return true;
                                    Player player = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
                                    return player.isSurvival();
                                }
                        ), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(new NearestPlayerSensor(16, 0, 20),
                        new NearestEntitySensor(EntityCat.class, CoreMemoryTypes.NEAREST_SHARED_ENTITY, 42, 0),
                        new NearestEntitySensor(EntityOcelot.class, CoreMemoryTypes.NEAREST_SHARED_ENTITY, 42, 0),
                        entity -> {
                            var memoryStorage = entity.getMemoryStorage();
                            Entity attacker = memoryStorage.get(CoreMemoryTypes.ATTACK_TARGET);
                            if (attacker == null)
                                attacker = memoryStorage.get(CoreMemoryTypes.NEAREST_PLAYER);
                            if (attacker != null && (!(attacker instanceof Player player) || player.isSurvival()) && attacker.distanceSquared(entity) <= 3 * 3 && (memoryStorage.isEmpty(CoreMemoryTypes.SHOULD_EXPLODE) || memoryStorage.compareDataTo(CoreMemoryTypes.SHOULD_EXPLODE, false))) {
                                memoryStorage.put(CoreMemoryTypes.SHOULD_EXPLODE, true);
                                return;
                            }
                            if ((attacker == null || (attacker instanceof Player player && !player.isSurvival()) || attacker.distanceSquared(entity) >= 7 * 7) && memoryStorage.compareDataTo(CoreMemoryTypes.SHOULD_EXPLODE, true) && memoryStorage.get(CoreMemoryTypes.EXPLODE_CANCELLABLE)) {
                                memoryStorage.put(CoreMemoryTypes.SHOULD_EXPLODE, false);
                            }
                        })
                .controllers(new WalkController(), new LookController(true, true), new FluctuateController())
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    private boolean hasClearLineOfSight(Entity target) {
        return target != null && this.getLevel().raycastBlocks(this, target).stream()
                .noneMatch(block -> this.getLevel().blocksBlockSight(block, false, false));
    }


    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    @Override
    public float getFloatingHeight() {
        return 0.6f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(20);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.2f);
    }

    public boolean isPowered() {
        return getDataFlag(ActorFlags.POWERED);
    }

    public void setPowered(EntityLightningStrike bolt) {
        CreeperPowerEvent ev = new CreeperPowerEvent(this, bolt, CreeperPowerEvent.PowerCause.LIGHTNING);
        this.getServer().getPluginManager().callEvent(ev);

        if (!ev.isCancelled()) {
            this.setDataFlag(ActorFlags.POWERED, true);
            this.nbt.putBoolean("powered", true);
        }
    }

    public void setPowered(boolean powered) {
        CreeperPowerEvent ev = new CreeperPowerEvent(this, powered ? CreeperPowerEvent.PowerCause.SET_ON : CreeperPowerEvent.PowerCause.SET_OFF);
        this.getServer().getPluginManager().callEvent(ev);

        if (!ev.isCancelled()) {
            this.setDataFlag(ActorFlags.POWERED, powered);
            this.nbt.putBoolean("powered", powered);
        }
    }

    @Override
    public void onStruckByLightning(Entity entity) {
        this.setPowered(true);
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        final CompoundTag nbtMap = this.getNbt();
        if (nbtMap.getBoolean("powered") || nbtMap.getBoolean("IsPowered")) {
            this.setDataFlag(ActorFlags.POWERED, true, false);
        }
        this.actorDataMap.put(ActorDataTypes.SWELL, (byte) 0);
    }

    @Override
    public String getOriginalName() {
        return "Creeper";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("creeper", "monster", "mob");
    }

    @Override
    public Item[] getDrops(@NotNull Item weapon) {
        if (!(this.lastDamageCause instanceof EntityDamageByEntityEvent)) {
            return Item.EMPTY_ARRAY;
        }

        int looting = weapon.getEnchantmentLevel(Enchantment.ID_LOOTING);

        int gunpowder = Utils.rand(0, 2 + looting);
        if (gunpowder <= 0) {
            return Item.EMPTY_ARRAY;
        }

        return new Item[]{
                Item.get(Item.GUNPOWDER, 0, gunpowder)
        };
    }


    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        var memoryStorage = this.getMemoryStorage();
        if (item.getId() == Item.FLINT_AND_STEEL && (memoryStorage.isEmpty(CoreMemoryTypes.SHOULD_EXPLODE) || memoryStorage.compareDataTo(CoreMemoryTypes.SHOULD_EXPLODE, false))) {
            memoryStorage.put(CoreMemoryTypes.SHOULD_EXPLODE, true);
            memoryStorage.put(CoreMemoryTypes.EXPLODE_CANCELLABLE, false);
            this.level.addSound(this, Sound.FIRE_IGNITE);
            return true;
        }
        return super.onInteract(player, item, clickedPos);
    }

    @Override
    public String getInteractButtonText(Player player) {
        return "action.interact.creeper";
    }

    @Override
    public boolean canDoInteraction() {
        return true;
    }
}
