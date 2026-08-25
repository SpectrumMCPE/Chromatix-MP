package chromatix.entity.projectile;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.entity.EntityExplosive;
import chromatix.event.entity.EntityDamageByEntityEvent;
import chromatix.event.entity.EntityDamageEvent;
import chromatix.event.entity.EntityExplosionPrimeEvent;
import chromatix.level.Explosion;
import chromatix.level.GameRule;
import chromatix.level.Position;
import chromatix.level.format.IChunk;
import chromatix.level.vibration.VibrationEvent;
import chromatix.level.vibration.VibrationType;
import chromatix.math.Vector3;
import chromatix.nbt.tag.CompoundTag;
import org.jetbrains.annotations.NotNull;


public class EntityFireball extends EntitySmallFireball implements EntityExplosive {

    public Entity directionChanged;

    @Override
    @NotNull public String getIdentifier() {
        return FIREBALL;
    }

    public EntityFireball(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }


    @Override
    public float getWidth() {
        return 0.31f;
    }

    @Override
    public float getHeight() {
        return 0.31f;
    }

    @Override
    public float getLength() {
        return 0.31f;
    }

    @Override
    public void onCollideWithEntity(Entity entity) {
        if(directionChanged != null) {
            if(directionChanged == entity) return;
        }
        explode();
        super.onCollideWithEntity(entity);
    }

    @Override
    protected void onCollideWithBlock(Position position, Vector3 motion) {
        this.level.getVibrationManager().callVibrationEvent(new VibrationEvent(this, this.getVector3(), VibrationType.PROJECTILE_LAND));
        boolean affect = false;
        for (Block collisionBlock : level.getCollisionBlocks(getBoundingBox().grow(0.1, 0.1, 0.1)))
            affect = onCollideWithBlock(position, motion, collisionBlock);
        if (!affect && this.getLevelBlock().isAir()) {
            explode();
        }
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (this.directionChanged == null && source instanceof EntityDamageByEntityEvent event) {
            this.directionChanged = event.getDamager();
            this.setMotion(event.getDamager().getDirectionVector());
        }
        return true;
    }

    @Override
    public String getOriginalName() {
        return "FireBall";
    }

    @Override
    public void explode() {
        if (this.closed) {
            return;
        }
        this.close();
        EntityExplosionPrimeEvent ev = new EntityExplosionPrimeEvent(this, 1.2);
        ev.setFireChance(.4);
        this.server.getPluginManager().callEvent(ev);
        if (!ev.isCancelled()) {
            Explosion explosion = new Explosion(this, (float) ev.getForce(), this.shootingEntity);
            explosion.setFireChance(ev.getFireChance());
            if (ev.isBlockBreaking() && this.level.getGameRules().getBoolean(GameRule.MOB_GRIEFING)) {
                explosion.explodeA();
            }
            explosion.explodeB();
        }
    }
}
