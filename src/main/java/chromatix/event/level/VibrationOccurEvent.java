package chromatix.event.level;

import chromatix.event.HandlerList;

public class VibrationOccurEvent extends VibrationEvent{

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public VibrationOccurEvent(chromatix.level.vibration.VibrationEvent vibrationEvent) {
        super(vibrationEvent);
    }
}
