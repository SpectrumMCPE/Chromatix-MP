package chromatix.event.vehicle;

import chromatix.entity.Entity;
import chromatix.event.Event;

/**
 * @author larryTheCoder (Nukkit Project) 
 * @since 7/5/2017
 */
public abstract class VehicleEvent extends Event {

    private final Entity vehicle;

    public VehicleEvent(Entity vehicle) {
        this.vehicle = vehicle;
    }

    public Entity getVehicle() {
        return vehicle;
    }
}
