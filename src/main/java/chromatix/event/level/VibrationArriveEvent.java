package chromatix.event.level;

import chromatix.event.HandlerList;
import chromatix.level.vibration.VibrationListener;


public class VibrationArriveEvent extends VibrationEvent{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected VibrationListener listener;

    public VibrationArriveEvent(chromatix.level.vibration.VibrationEvent vibrationEvent, VibrationListener listener) {
        super(vibrationEvent);
        this.listener = listener;
    }
}
