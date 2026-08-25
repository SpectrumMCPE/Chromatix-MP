package chromatix.command.tree.node;

/**
 * Parses and validates experience level arguments as {@link Integer} values for Chromatix command trees.
 * <p>
 * This node is not used by default and must be manually specified. It parses arguments ending with 'l' or 'L' as experience levels.
 *
 * <b>Features:</b>
 * <ul>
 *   <li>Parses arguments ending with 'l' or 'L' as experience levels.</li>
 *   <li>Returns the integer value of the level or triggers an error if invalid.</li>
 *   <li>Used for experience level parameter parsing when manually specified.</li>
 * </ul>
 *
 * @author Chromatix Project Team
 * @see ParamNode
 * @since Chromatix 1.19.50
 */
public class XpLevelNode extends ParamNode<Integer> {
    @Override
    public void fill(String arg) {
        if (arg.endsWith("l") || arg.endsWith("L")) {
            try {
                this.value = Integer.parseInt(arg.substring(0, arg.length() - 1));
            } catch (NumberFormatException e) {
                this.error();
            }
        } else error();
    }
}
