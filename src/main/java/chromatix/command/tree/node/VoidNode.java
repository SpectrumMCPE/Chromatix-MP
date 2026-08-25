package chromatix.command.tree.node;

import chromatix.command.tree.ParamList;

/**
 * Represents a placeholder parameter node for Chromatix command trees.
 * <p>
 * This node is used as a placeholder for parameters that do not require a value. It always returns null and is always considered optional and filled.
 *
 * @author Chromatix Project Team
 * @see IParamNode
 * @since Chromatix 1.19.50
 */
public class VoidNode implements IParamNode<Void> {
    @Override
    public void fill(String arg) {
    }

    @Override
    public <E> E get() {
        return null;
    }

    @Override
    public void reset() {
    }

    @Override
    public ParamList getParamList() {
        return null;
    }

    @Override
    public boolean hasResult() {
        return true;
    }

    @Override
    public boolean isOptional() {
        return true;
    }
}
