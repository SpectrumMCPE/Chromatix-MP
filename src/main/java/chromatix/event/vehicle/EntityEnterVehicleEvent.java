package chromatix.event.vehicle;

import chromatix.Player;
import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


public class EntityEnterVehicleEvent extends VehicleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final chromatix.entity.Entity riding;

    public EntityEnterVehicleEvent(chromatix.entity.Entity riding, Entity vehicle) {
        super(vehicle);
        this.riding = riding;
    }

    public chromatix.entity.Entity getEntity() {
        return riding;
    }

    public boolean isPlayer() {
        return riding instanceof Player;
    }

}
