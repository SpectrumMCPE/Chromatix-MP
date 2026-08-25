package chromatix.event.entity;

import chromatix.entity.Entity;
import chromatix.entity.EntityCreature;
import chromatix.entity.EntityHuman;
import chromatix.entity.item.EntityItem;
import chromatix.entity.projectile.EntityProjectile;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.level.Position;

/**
 * @author MagicDroidX (Nukkit Project)
 */

public class EntitySpawnEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final int entityType;

    public EntitySpawnEvent(chromatix.entity.Entity entity) {
        this.entity = entity;
        this.entityType = entity.getNetworkId();
    }

    public Position getPosition() {
        return this.entity.getPosition();
    }

    public int getType() {
        return this.entityType;
    }

    public boolean isCreature() {
        return this.entity instanceof EntityCreature;
    }

    public boolean isHuman() {
        return this.entity instanceof EntityHuman;
    }

    public boolean isProjectile() {
        return this.entity instanceof EntityProjectile;
    }

    public boolean isVehicle() {
        return this.entity instanceof Entity;
    }

    public boolean isItem() {
        return this.entity instanceof EntityItem;
    }

}
