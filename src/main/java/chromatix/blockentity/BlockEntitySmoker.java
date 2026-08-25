package chromatix.blockentity;

import chromatix.block.Block;
import chromatix.inventory.SmeltingInventory;
import chromatix.inventory.SmokerInventory;
import chromatix.item.Item;
import chromatix.level.format.IChunk;
import chromatix.nbt.tag.CompoundTag;
import chromatix.recipe.SmeltingRecipe;


public class BlockEntitySmoker extends BlockEntityFurnace {

    public BlockEntitySmoker(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected String getFurnaceName() {
        return "Smoker";
    }

    @Override
    protected String getClientName() {
        return SMOKER;
    }

    @Override
    protected String getIdleBlockId() {
        return Block.SMOKER;
    }

    @Override
    protected String getBurningBlockId() {
        return Block.LIT_SMOKER;
    }

    @Override
    protected SmeltingInventory createInventory() {
        return new SmokerInventory(this);
    }

    @Override
    protected SmeltingRecipe matchRecipe(Item raw) {
        return this.server.getRecipeRegistry().findSmokerRecipe(raw);
    }

    @Override
    protected int getSpeedMultiplier() {
        return 2;
    }
}
