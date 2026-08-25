package chromatix.event.block;

import chromatix.block.Block;
import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

import javax.annotation.Nullable;


public class FarmLandDecayEvent extends BlockEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Entity entity;

    public FarmLandDecayEvent(@Nullable Entity entity, Block farm) {
        super(farm);
        this.entity = entity;
    }

    public @Nullable Entity getEntity() {
        return entity;
    }
}
