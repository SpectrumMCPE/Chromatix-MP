package chromatix.event.vehicle;

import chromatix.entity.Entity;
import chromatix.event.HandlerList;


public class VehicleUpdateEvent extends VehicleEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public VehicleUpdateEvent(Entity vehicle) {
        super(vehicle);
    }

}
