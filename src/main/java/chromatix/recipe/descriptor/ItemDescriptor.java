package chromatix.recipe.descriptor;

import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.RecipeIngredient;
import chromatix.item.Item;


public interface ItemDescriptor extends Cloneable {

    ItemDescriptorType getType();

    Item toItem();

    ItemDescriptor clone() throws CloneNotSupportedException;

    int getCount();

    default boolean match(Item item) {
        return false;
    }

    RecipeIngredient toNetwork();
}
