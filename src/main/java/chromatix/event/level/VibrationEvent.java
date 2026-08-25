package chromatix.event.level;

import chromatix.event.Cancellable;
import chromatix.event.Event;


public abstract class VibrationEvent extends Event implements Cancellable {

    protected chromatix.level.vibration.VibrationEvent vibrationEvent;

    public VibrationEvent(chromatix.level.vibration.VibrationEvent vibrationEvent) {
        this.vibrationEvent = vibrationEvent;
    }

    public chromatix.level.vibration.VibrationEvent getVibrationEvent() {
        return vibrationEvent;
    }
}
