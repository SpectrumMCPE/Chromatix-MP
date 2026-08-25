package chromatix.entity.mob;

import chromatix.Player;
import chromatix.Server;
import chromatix.block.Block;
import chromatix.block.BlockCreakingHeart;
import chromatix.block.BlockPaleOakLog;
import chromatix.block.BlockResinClump;
import chromatix.block.property.CommonBlockProperties;
import chromatix.blockentity.BlockEntityCreakingHeart;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.DoNothingExecutor;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.MeleeAttackExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.ai.sensor.PlayerStaringSensor;
import chromatix.entity.components.MovementComponent;
import chromatix.entity.data.property.EntityProperty;
import chromatix.entity.data.property.EnumEntityProperty;
import chromatix.entity.data.property.IntEntityProperty;
import chromatix.event.entity.EntityDamageByEntityEvent;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.math.BlockFace;
import chromatix.math.Vector3;
import chromatix.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.Setter;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventGenericPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class EntityCreaking extends EntityMob {
    private final static String PROPERTY_CREAKING = "minecraft:creaking_state";
    private final static String PROPERTY_SWAYING_TICKS = "minecraft:creaking_swaying_ticks";

    public static final EntityProperty[] PROPERTIES = new EntityProperty[]{
            new EnumEntityProperty(PROPERTY_CREAKING, new String[]{
                    "neutral",
                    "hostile_observed",
                    "hostile_unobserved",
                    "twitching",
                    "crumbling"
            }, "neutral", true),
            new IntEntityProperty(PROPERTY_SWAYING_TICKS, 0, 0, 6, true)
    };


    @Override
    @NotNull
    public String getIdentifier() {
        return CREAKING;
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("creaking", "monster", "mob");
    }

    @Getter
    @Setter
    protected BlockEntityCreakingHeart creakingHeart;

    public EntityCreaking(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        // TODO: creaking inmobile state
        float behaviorMovement = false ? 0f : 0.4f;
        return MovementComponent.value(behaviorMovement);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new DoNothingExecutor(), new EntityCheckEvaluator(CoreMemoryTypes.STARING_PLAYER), 5, 1),
                        new Behavior(new PlaySoundExecutor(Sound.MOB_CREAKING_AMBIENT), all(new RandomSoundEvaluator(), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET)), 4, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 40, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 40, false, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(
                        new PlayerStaringCreakingSensor(40, 70, true),
                        new NearestPlayerCreakingSensor(40, 0, 0)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    protected void initEntity() {
        this.setHealthMax(1);
        this.diffHandDamage = new float[]{2.5f, 3, 4.5f};
        final CompoundTag nbtMap = this.getNbt();
        if (nbtMap.containsCompound("creakingHeart")) {
            CompoundTag tag = nbtMap.getCompound("creakingHeart");
            Vector3 vec = new Vector3(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
            if (getLevel().getBlock(vec, true) instanceof BlockCreakingHeart heart) {
                heart.getOrCreateBlockEntity().setLinkedCreaking(this);
            }
        }
        super.initEntity();
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (source.isCancelled()) return false;
        if (creakingHeart == null) return super.attack(source);
        if (this.isClosed() || !this.isAlive()) {
            return false;
        }
        if (source instanceof EntityDamageByEntityEvent entityDamageByEntityEvent && !(entityDamageByEntityEvent.getDamager() instanceof EntityCreeper)) {
            getMemoryStorage().put(CoreMemoryTypes.ATTACK_TARGET, entityDamageByEntityEvent.getDamager());
        }
        var storage = getMemoryStorage();
        if (storage != null) {
            storage.put(CoreMemoryTypes.BE_ATTACKED_EVENT, source);
            storage.put(CoreMemoryTypes.LAST_BE_ATTACKED_TIME, getLevel().getTick());
        }

        Block[] paleLogs = Arrays.stream(getLevel().getCollisionBlocks(creakingHeart.getLevelBlock().getBoundingBox().grow(2, 2, 2))).filter(block -> block instanceof BlockPaleOakLog).toArray(Block[]::new);
        int maxResinSpawn = ThreadLocalRandom.current().nextInt(1, 3);
        int resinSpawned = 0;
        logs:
        for (Block log : paleLogs) {
            for (BlockFace face : BlockFace.values()) {
                Block side = log.getSide(face);
                if (side.isAir()) {
                    BlockResinClump clump = (BlockResinClump) Block.get(Block.RESIN_CLUMP);
                    clump.setPropertyValue(CommonBlockProperties.MULTI_FACE_DIRECTION_BITS, clump.getPropertyValue(CommonBlockProperties.MULTI_FACE_DIRECTION_BITS) | (0b000001 << face.getOpposite().getDUSWNEIndex()));
                    side.getLevel().setBlock(side, clump);
                    resinSpawned++;
                    if (resinSpawned >= maxResinSpawn) break logs;
                }
            }
        }
        return true;
    }

    public void sendParticleTrail() {
        final LevelEventGenericPacket packet = new LevelEventGenericPacket();
        packet.setType(LevelEvent.PARTICLE_CREAKING_HEART_TRIAL);
        packet.setTag(NbtMap.builder()
                .putInt("CreakingAmount", 1)
                .putFloat("CreakingX", (float) this.x)
                .putFloat("CreakingY", (float) this.y)
                .putFloat("CreakingZ", (float) this.z)
                .putInt("HeartAmount", 1)
                .putFloat("HeartX", (float) creakingHeart.x)
                .putFloat("HeartY", (float) creakingHeart.y)
                .putFloat("HeartZ", (float) creakingHeart.z)
                .build()
        );
        Server.broadcastPacket(this.getViewers().values(), packet);
    }

    public void spawnResin() {

    }

    @Override
    public void kill() {
        //ToDo: Creaking Death Animation
        super.kill();
        if (creakingHeart != null && creakingHeart.isBlockEntityValid()) {
            creakingHeart.setLinkedCreaking(null);
        }
    }

    @Override
    public void saveNBT() {
        if (creakingHeart != null) {
            this.nbt.putCompound("creakingHeart", new CompoundTag()
                    .putInt("x", creakingHeart.getFloorX())
                    .putInt("y", creakingHeart.getFloorY())
                    .putInt("z", creakingHeart.getFloorZ()));
        }
        super.saveNBT();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (!(!getLevel().isDay() || getLevel().isRaining() || getLevel().isThundering())) {
            this.kill();
        }
        if (creakingHeart != null) {
            if (this.distance(creakingHeart) > 32) {
                setMoveTarget(creakingHeart);
                setLookTarget(creakingHeart);
            }
            if (getMemoryStorage().notEmpty(CoreMemoryTypes.LAST_BE_ATTACKED_TIME)) {
                if (getLevel().getTick() - getMemoryStorage().get(CoreMemoryTypes.LAST_BE_ATTACKED_TIME) < 51) {
                    sendParticleTrail();
                }
            }
        }
        return super.onUpdate(currentTick);
    }

    @Override
    public float getHeight() {
        return 2.5F;
    }

    @Override
    public float getWidth() {
        return 1F;
    }

    @Override
    public Integer getExperienceDrops() {
        return 0;
    }

    @Override
    public void updateMovement() {
        if (!this.isAlive() || this.isClosed()) return;

        super.updateMovement();

        if (this.ticksLived < 5) return;

        try {
            if (creakingHeart != null && creakingHeart.isBlockEntityValid()) {
                creakingHeart.getHeart().updateAroundRedstone(BlockFace.UP, BlockFace.DOWN);
            } else kill();
        } catch (Exception e) {
            //can happen when you regenerate a chunk with debug command.
            kill();
        }
    }

    private class NearestPlayerCreakingSensor extends NearestPlayerSensor {

        public NearestPlayerCreakingSensor(double range, double minRange, int period) {
            super(range, minRange, period);
        }

        @Override
        public void sense(EntityIntelligent entity) {
            Player before = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
            super.sense(entity);
            Player after = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
            if (before != after) {
                if (before == null) {
                    entity.level.addSound(entity, Sound.MOB_CREAKING_ACTIVATE);
                } else if (after == null) {
                    entity.level.addSound(entity, Sound.MOB_CREAKING_DEACTIVATE);
                }
            }
        }
    }

    private class PlayerStaringCreakingSensor extends PlayerStaringSensor {

        public PlayerStaringCreakingSensor(double range, double triggerDiff, boolean ignoreRotation) {
            super(range, triggerDiff, ignoreRotation);
        }

        @Override
        public void sense(EntityIntelligent entity) {
            Player before = entity.getMemoryStorage().get(CoreMemoryTypes.STARING_PLAYER);
            super.sense(entity);
            Player after = entity.getMemoryStorage().get(CoreMemoryTypes.STARING_PLAYER);
            if (before != after) {
                if (before == null) {
                    entity.level.addSound(entity, Sound.MOB_CREAKING_FREEZE);
                } else if (after == null) {
                    entity.level.addSound(entity, Sound.MOB_CREAKING_UNFREEZE);
                }
            }
        }

    }
}
