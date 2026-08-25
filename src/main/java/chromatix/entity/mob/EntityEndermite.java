package chromatix.entity.mob;

import chromatix.Player;
import chromatix.block.BlockSoulSand;
import chromatix.entity.EntityArthropod;
import chromatix.entity.EntityWalkable;
import chromatix.entity.ai.behavior.Behavior;
import chromatix.entity.ai.behaviorgroup.BehaviorGroup;
import chromatix.entity.ai.behaviorgroup.IBehaviorGroup;
import chromatix.entity.ai.controller.LookController;
import chromatix.entity.ai.controller.WalkController;
import chromatix.entity.ai.evaluator.EntityCheckEvaluator;
import chromatix.entity.ai.evaluator.RandomSoundEvaluator;
import chromatix.entity.ai.executor.FlatRandomRoamExecutor;
import chromatix.entity.ai.executor.MeleeAttackExecutor;
import chromatix.entity.ai.executor.PlaySoundExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.entity.ai.route.finder.impl.SimpleFlatAStarRouteFinder;
import chromatix.entity.ai.route.posevaluator.WalkingPosEvaluator;
import chromatix.entity.ai.sensor.NearestEntitySensor;
import chromatix.entity.ai.sensor.NearestPlayerSensor;
import chromatix.entity.components.HealthComponent;
import chromatix.entity.components.MovementComponent;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.level.Sound;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author Box.
 */
public class EntityEndermite extends EntityMob implements EntityWalkable, EntityArthropod {

    @Override
    @NotNull public String getIdentifier() {
        return ENDERMITE;
    }

    public EntityEndermite(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public IBehaviorGroup requireBehaviorGroup() {
        return BehaviorGroup.builder(this)
                .behaviors(
                        new Behavior(new PlaySoundExecutor(Sound.MOB_ENDERMITE_SAY), new RandomSoundEvaluator(), 6, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.ATTACK_TARGET, 0.3f, 16, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.ATTACK_TARGET), 4, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_SHARED_ENTITY, 0.3f, 16, true, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_SHARED_ENTITY), 3, 1),
                        new Behavior(new MeleeAttackExecutor(CoreMemoryTypes.NEAREST_PLAYER, 0.3f, 16, false, 30), new EntityCheckEvaluator(CoreMemoryTypes.NEAREST_PLAYER), 2, 1),
                        new Behavior(new FlatRandomRoamExecutor(0.3f, 12, 100, false, -1, true, 10), none(), 1, 1)
                )
                .sensors(
                        new NearestPlayerSensor(16, 0, 0),
                        new NearestEntitySensor(EntityIronGolem.class, CoreMemoryTypes.NEAREST_SHARED_ENTITY, 16, 0),
                        new NearestEntitySensor(EntityEnderman.class, CoreMemoryTypes.NEAREST_SHARED_ENTITY, 16, 0)
                )
                .controllers(new WalkController(), new LookController(true, true))
                .routeFinder(new SimpleFlatAStarRouteFinder(new WalkingPosEvaluator(), this))
                .build();
    }

    @Override
    protected void initEntity() {
        this.diffHandDamage = new float[]{2f, 2f, 3f};
        super.initEntity();
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if(currentTick%10 == 0) {
            if(getLevelBlock().down() instanceof BlockSoulSand) {
                this.attack(new EntityDamageEvent(this, EntityDamageEvent.DamageCause.SUFFOCATION, 1));
            }
        }
        return super.onUpdate(currentTick);
    }

    @Override
    public float getWidth() {
        return 0.4f;
    }

    @Override
    public float getHeight() {
        return 0.3f;
    }

    @Override
    public HealthComponent getComponentHealth() {
        return HealthComponent.value(8);
    }

    @Override
    protected @Nullable MovementComponent getComponentMovement() {
        return MovementComponent.value(0.25f);
    }

    @Override
    public String getOriginalName() {
        return "Endermite";
    }

    @Override
    public Set<String> typeFamily() {
        return Set.of("endermite", "arthropod", "monster", "lightweight", "mob");
    }

    @Override
    public boolean isPreventingSleep(Player player) {
        return true;
    }

    @Override
    public Integer getExperienceDrops() {
        return 3;
    }
}
