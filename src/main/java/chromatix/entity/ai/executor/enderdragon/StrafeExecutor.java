package chromatix.entity.ai.executor.enderdragon;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.entity.EntityID;
import chromatix.entity.EntityIntelligent;
import chromatix.entity.ai.executor.EntityControl;
import chromatix.entity.ai.executor.IBehaviorExecutor;
import chromatix.entity.ai.memory.CoreMemoryTypes;
import chromatix.level.Location;
import chromatix.math.BVector3;
import chromatix.math.Vector3;
import chromatix.nbt.tag.CompoundTag;
import chromatix.nbt.tag.DoubleTag;
import chromatix.nbt.tag.FloatTag;
import chromatix.nbt.tag.ListTag;

public class StrafeExecutor implements EntityControl, IBehaviorExecutor {

    private boolean fired = false;

    public StrafeExecutor() {
    }

    @Override
    public boolean execute(EntityIntelligent entity) {

        if (fired) return false;

        Player player = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
        if (player == null) return false;
        setLookTarget(entity, player);
        setRouteTarget(entity, player);

        if (entity.distance(player) <= 64) {

            Vector3 toPlayerVector = new Vector3(player.x - entity.x, player.y - entity.y, player.z - entity.z).normalize();

            Location fireballLocation = entity.getLocation().add(toPlayerVector.multiply(5));
            double yaw = BVector3.getYawFromVector(toPlayerVector);
            double pitch = BVector3.getPitchFromVector(toPlayerVector);
            CompoundTag nbt = new CompoundTag()
                    .putList("Pos", new ListTag<DoubleTag>()
                            .add(new DoubleTag(fireballLocation.x))
                            .add(new DoubleTag(fireballLocation.y))
                            .add(new DoubleTag(fireballLocation.z)))
                    .putList("Motion", new ListTag<DoubleTag>()
                            .add(new DoubleTag(-Math.sin(yaw / 180 * Math.PI) * Math.cos(pitch / 180 * Math.PI)))
                            .add(new DoubleTag(-Math.sin(pitch / 180 * Math.PI)))
                            .add(new DoubleTag(Math.cos(yaw / 180 * Math.PI) * Math.cos(pitch / 180 * Math.PI))))
                    .putList("Rotation", new ListTag<FloatTag>()
                            .add(new FloatTag(0))
                            .add(new FloatTag(0)));

            Entity projectile = Entity.createEntity(EntityID.DRAGON_FIREBALL, entity.level.getChunk(entity.getChunkX(), entity.getChunkZ()), nbt);
            projectile.spawnToAll();
            this.fired = true;
            return false;
        }
        return true;
    }


    @Override
    public void onStart(EntityIntelligent entity) {
        Player player = entity.getMemoryStorage().get(CoreMemoryTypes.NEAREST_PLAYER);
        if (player == null) return;
        setLookTarget(entity, player);
        setRouteTarget(entity, player);
        this.fired = false;
    }

    @Override
    public void onStop(EntityIntelligent entity) {
        entity.setEnablePitch(false);
        entity.getMemoryStorage().clear(CoreMemoryTypes.LAST_ENDER_CRYSTAL_DESTROY);
    }

    @Override
    public void onInterrupt(EntityIntelligent entity) {
        onStop(entity);
    }

}
