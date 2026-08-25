package chromatix.command.tree.node;


/**
 * Parses a command parameter as a {@link String} value for wildcard target parameters in Chromatix command trees.
 * <p>
 * This node is used for all command parameters of type {@link chromatix.command.data.CommandParamType#WILDCARD_TARGET WILDCARD_TARGET}
 * if no custom {@link IParamNode} is specified. It simply sets the argument as the node value and does not perform validation.
 *
 * @author Chromatix Project Team
 * @see StringNode
 * @since Chromatix 1.19.50
 * <p>
 * Parsed as {@link String} value
 * <p>
 * All command parameters are of type {@link chromatix.command.data.CommandParamType#WILDCARD_TARGET WILDCARD_TARGET}. If no {@link IParamNode} is manually specified, this parser is used by default.
 */
public class WildcardTargetStringNode extends StringNode {

    @Override
    public void fill(String arg) {
        //WILDCARD_TARGET can never fail to parse
        this.value = arg;
    }

}
