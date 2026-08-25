package chromatix.event.entity;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class EntityFallEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private Block blockFallOn;
    private float fallDistance;

    public EntityFallEvent(Entity entity, Block blockFallOn, float fallDistance) {
        this.entity = entity;
        this.blockFallOn = blockFallOn;
        this.fallDistance = fallDistance;
    }

    public Block getBlockFallOn() {
        return blockFallOn;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public void setFallDistance(float distance) {
        this.fallDistance = distance;
    }
}
