package chromatix.event.vehicle;


import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


public class VehicleCreateEvent extends VehicleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public VehicleCreateEvent(Entity vehicle) {
        super(vehicle);
    }

}
