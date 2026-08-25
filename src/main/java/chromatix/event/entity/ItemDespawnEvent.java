package chromatix.event.entity;

import chromatix.entity.item.EntityItem;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class ItemDespawnEvent extends EntityEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public ItemDespawnEvent(EntityItem item) {
        this.entity = item;
    }

    @Override
    public EntityItem getEntity() {
        return (EntityItem) this.entity;
    }
}
