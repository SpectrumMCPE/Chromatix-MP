package chromatix.command.selector.args.impl;

import chromatix.command.CommandSender;
import chromatix.command.exceptions.SelectorSyntaxException;
import chromatix.command.selector.ParseUtils;
import chromatix.command.selector.SelectorType;
import chromatix.command.selector.args.ISelectorArgument;
import chromatix.entity.Entity;
import chromatix.level.Location;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * Selector argument implementation for the 'rm' parameter in Minecraft selectors (Chromatix).
 * <p>
 * The 'rm' argument is used to filter entities by their minimum distance from a reference location, selecting only those
 * whose distance is greater than the specified value. This is commonly used in selectors such as @e[rm=10] to select entities
 * farther than 10 blocks from the base position.
 * <p>
 * <b>Features:</b>
 * <ul>
 *   <li>Supports the 'rm' selector argument for minimum radius-based distance filtering.</li>
 *   <li>Validates that only one argument is provided and that it is not negated.</li>
 *   <li>Parses the argument as a double and applies the filter to all entities.</li>
 *   <li>Returns a predicate that checks if an entity's distance squared to the base position is greater than the square of the specified minimum radius.</li>
 *   <li>Integrates with the Chromatix selector argument system via {@link ISelectorArgument}.</li>
 * </ul>
 * <p>
 * <b>Usage:</b>
 * <ul>
 *   <li>Used internally by the entity selector system for @e[rm=...] and similar selectors.</li>
 *   <li>Not intended for direct instantiation; registered automatically.</li>
 * </ul>
 * <p>
 * <b>Example:</b>
 * <pre>
 * // @e[rm=5] selects entities farther than 5 blocks from the base position
 * </pre>
 *
 * <b>Argument Rules:</b>
 * <ul>
 *   <li>Only one argument is allowed (throws if more).</li>
 *   <li>Argument must not be negated (throws if starts with '!').</li>
 *   <li>Argument must be a valid double value.</li>
 * </ul>
 *
 * @author Chromatix Project Team
 * @see ISelectorArgument
 * @see chromatix.command.selector.ParseUtils
 * @see chromatix.command.selector.SelectorType
 * @see chromatix.command.CommandSender
 * @see chromatix.level.Location
 * @see chromatix.entity.Entity
 * @since Chromatix 2.0.0
 */
public class RM implements ISelectorArgument {
    @Override
    public @Nullable Predicate<Entity> getPredicate(SelectorType selectorType, CommandSender sender, Location basePos, String... arguments) throws SelectorSyntaxException {
        ParseUtils.singleArgument(arguments, getKeyName());
        ParseUtils.cannotReversed(arguments[0]);
        final var rm = Double.parseDouble(arguments[0]);
        return entity -> entity.distanceSquared(basePos) > Math.pow(rm, 2);
    }

    /**
     * Returns the key name for this selector argument ('rm').
     *
     * @return the string "rm"
     */
    @Override
    public String getKeyName() {
        return "rm";
    }

    /**
     * Returns the priority for this argument (3).
     *
     * @return the integer 3
     */
    @Override
    public int getPriority() {
        return 3;
    }
}
