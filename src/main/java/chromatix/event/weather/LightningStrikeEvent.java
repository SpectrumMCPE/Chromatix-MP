package chromatix.event.weather;

import chromatix.entity.weather.EntityLightningStrike;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.event.level.WeatherEvent;
import chromatix.level.Level;

/**
 * @author funcraft (Nukkit Project)
 */
public class LightningStrikeEvent extends WeatherEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityLightningStrike bolt;

    public LightningStrikeEvent(Level level, final EntityLightningStrike bolt) {
        super(level);
        this.bolt = bolt;
    }

    /**
     * Gets the bolt which is striking the earth.
     * @return lightning entity
     */
    public EntityLightningStrike getLightning() {
        return bolt;
    }

}
