package chromatix.command.selector.args.impl;

import chromatix.command.CommandSender;
import chromatix.command.selector.SelectorType;
import chromatix.command.selector.args.ISelectorArgument;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Abstract base class for coordinate range selector arguments (such as dx, dy, dz) in Chromatix.
 * <p>
 * ScopeArgument provides a foundation for implementing selector arguments that define a range along a specific axis
 * (X, Y, or Z) for entity selection. Typical subclasses include arguments for dx, dy, and dz, which are used to specify
 * the size of the selection area along each axis in Minecraft selectors (e.g., @e[dx=10,dy=5,dz=3]).
 * <p>
 * <b>Features:</b>
 * <ul>
 *   <li>Defines a common priority for all scope arguments (priority = 2).</li>
 *   <li>Implements the {@link chromatix.command.selector.args.ISelectorArgument} interface for integration with the selector system.</li>
 *   <li>Provides a default value of "0" for dx, dy, or dz if any of them are present in the argument map.</li>
 *   <li>Intended to be subclassed for specific axis range argument implementations.</li>
 * </ul>
 * <p>
 * <b>Usage:</b>
 * <ul>
 *   <li>Extend this class to implement a scope argument (e.g., DX, DY, DZ).</li>
 *   <li>Override required methods from {@link chromatix.command.selector.args.ISelectorArgument} to provide argument-specific logic.</li>
 *   <li>Register the subclass with the selector API to enable axis-based range filtering.</li>
 * </ul>
 * <p>
 * <b>Example:</b>
 * <pre>
 * public class DX extends ScopeArgument {
 *     @Override
 *     public String getKeyName() { return "dx"; }
 *     // Implement predicate/filter logic...
 * }
 * </pre>
 *
 * @author Chromatix Project Team
 * @see chromatix.command.selector.args.ISelectorArgument
 * @see chromatix.command.selector.EntitySelectorAPI
 * @since Chromatix 2.0.0
 */


public abstract class ScopeArgument implements ISelectorArgument {
    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public @Nullable String getDefaultValue(Map<String, List<String>> values, SelectorType selectorType, CommandSender sender) {
        if (values.containsKey("dx") || values.containsKey("dy") || values.containsKey("dz"))
            return "0";
        return null;
    }
}
