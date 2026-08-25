package chromatix.recipe;

import chromatix.item.Item;
import chromatix.recipe.descriptor.DefaultDescriptor;
import chromatix.recipe.descriptor.ItemDescriptor;
import chromatix.registry.RecipeRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;


public class SmokerRecipe extends SmeltingRecipe {
    public SmokerRecipe(Item result, Item ingredient) {
        this(null, result, ingredient);
    }

    public SmokerRecipe(@Nullable String recipeId, Item result, Item ingredient) {
        super(recipeId == null ?
                RecipeRegistry.computeRecipeId(List.of(result), List.of(new DefaultDescriptor(ingredient)), RecipeType.SMOKER) :
                recipeId);
        this.ingredients.add(new DefaultDescriptor(ingredient.clone()));
        this.results.add(result.clone());
    }

    public SmokerRecipe(String recipeId, UUID uuid, int netId, int priority, Item result, ItemDescriptor ingredient) {
        super(recipeId, uuid, netId, priority);
        this.ingredients.add(ingredient);
        this.results.add(result.clone());
    }

    @Override
    public boolean match(Input input) {
        return true;
    }

    @Override
    public RecipeType getType() {
        return RecipeType.SMOKER;
    }

    @Override
    public String getRecipeIdTag() {
        return "smoker";
    }
}
