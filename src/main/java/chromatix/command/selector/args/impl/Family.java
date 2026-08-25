package chromatix.command.selector.args.impl;

import chromatix.command.CommandSender;
import chromatix.command.selector.ParseUtils;
import chromatix.command.selector.SelectorType;
import chromatix.command.selector.args.CachedSimpleSelectorArgument;
import chromatix.entity.Entity;
import chromatix.level.Location;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Selector argument implementation for the 'family' parameter in Minecraft selectors (Chromatix).
 * <p>
 * The 'family' argument is used to filter entities by their family tags. It allows specifying required families
 * (entities must have all specified families) and excluded families (entities must not have any of the specified families).
 * Negation is supported by prefixing a family name with '!' (e.g., @e[family=!undead]).
 * <p>
 * <b>Features:</b>
 * <ul>
 *   <li>Supports the 'family' selector argument for entity family-based filtering.</li>
 *   <li>Allows multiple family names, with negation for exclusion.</li>
 *   <li>Parses arguments into required and excluded family lists.</li>
 *   <li>Returns a predicate that checks if an entity matches all required families and none of the excluded families.</li>
 *   <li>Integrates with the Chromatix selector argument system via {@link CachedSimpleSelectorArgument}.</li>
 * </ul>
 * <p>
 * <b>Usage:</b>
 * <ul>
 *   <li>Used internally by the entity selector system for @e[family=...] and similar selectors.</li>
 *   <li>Not intended for direct instantiation; registered automatically.</li>
 * </ul>
 * <p>
 * <b>Example:</b>
 * <pre>
 * // @e[family=undead] selects entities with the 'undead' family
 * // @e[family=!undead] selects entities without the 'undead' family
 * // @e[family=undead,arthropod] selects entities with both 'undead' and 'arthropod' families
 * // @e[family=undead,!arthropod] selects entities with 'undead' but not 'arthropod'
 * </pre>
 *
 * <b>Argument Rules:</b>
 * <ul>
 *   <li>Multiple arguments are allowed (comma-separated).</li>
 *   <li>Negation is supported by prefixing with '!'.</li>
 *   <li>Empty or null arguments are ignored.</li>
 * </ul>
 *
 * @author Chromatix Project Team
 * @see CachedSimpleSelectorArgument
 * @see chromatix.command.selector.ParseUtils
 * @see chromatix.command.selector.SelectorType
 * @see chromatix.command.CommandSender
 * @see chromatix.level.Location
 * @see chromatix.entity.Entity
 * @since Chromatix 2.0.0
 */
public class Family extends CachedSimpleSelectorArgument {

    @Override
    protected Predicate<Entity> cache(SelectorType selectorType, CommandSender sender, Location basePos, String... arguments) {
        final var have     = new ArrayList<String>();
        final var dontHave = new ArrayList<String>();

        if (arguments != null) {
            for (var fam : arguments) {
                if (fam == null || fam.isEmpty()) continue;
                boolean reversed = ParseUtils.checkReversed(fam);
                if (reversed) {
                    fam = fam.substring(1);
                    if (!fam.isEmpty()) dontHave.add(fam);
                } else {
                    have.add(fam);
                }
            }
        }

        return entity ->
                (have.isEmpty() || entity.isAllFamilies(have))
                && (dontHave.isEmpty() || !entity.isAnyFamily(dontHave));
    }

    @Override
    public String getKeyName() {
        return "family";
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
