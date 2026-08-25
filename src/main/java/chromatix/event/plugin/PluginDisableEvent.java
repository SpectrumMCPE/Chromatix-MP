package chromatix.event.plugin;

import chromatix.plugin.Plugin;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class PluginDisableEvent extends PluginEvent {
    public PluginDisableEvent(Plugin plugin) {
        super(plugin);
    }
}
