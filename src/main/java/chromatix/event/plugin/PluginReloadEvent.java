package chromatix.event.plugin;

import chromatix.event.Cancellable;
import chromatix.plugin.Plugin;

/**
 * Triggers before plugin is reloaded.
 *
 * @author xRookieFight
 * @since 20/12/2025
 */
public class PluginReloadEvent extends PluginEvent implements Cancellable {
    public PluginReloadEvent(Plugin plugin) {
        super(plugin);
    }
}
