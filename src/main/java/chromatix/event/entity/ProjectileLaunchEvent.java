package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.entity.projectile.EntityProjectile;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class ProjectileLaunchEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected Entity shooter;

    public ProjectileLaunchEvent(EntityProjectile entity, Entity shooter) {
        this.shooter = shooter;
        this.entity = entity;
    }

    @Override
    public EntityProjectile getEntity() {
        return (EntityProjectile) this.entity;
    }

    public Entity getShooter() {
        return this.shooter;
    }
}
