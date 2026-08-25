package chromatix.event.vehicle;

import chromatix.entity.Entity;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;


/**
 * Is called when an vehicle gets destroyed
 */
public class VehicleDestroyEvent extends VehicleEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Constructor for the VehicleDestroyEvent
     *
     * @param vehicle the destroyed vehicle
     */
    public VehicleDestroyEvent(final Entity vehicle) {
        super(vehicle);
    }

}
