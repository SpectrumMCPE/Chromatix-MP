package chromatix.plugin;

import chromatix.event.Event;
import chromatix.event.Listener;
import chromatix.utils.EventException;

/**
 * @author iNevet (Nukkit Project)
 */
public interface EventExecutor {

    void execute(Listener listener, Event event) throws EventException;
}
