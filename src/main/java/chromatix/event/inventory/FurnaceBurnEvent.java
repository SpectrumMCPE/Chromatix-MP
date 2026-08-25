package chromatix.event.inventory;

import chromatix.blockentity.BlockEntityFurnace;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.event.block.BlockEvent;
import chromatix.item.Item;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class FurnaceBurnEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final BlockEntityFurnace furnace;
    private final Item fuel;
    private int burnTime;
    private boolean burning = true;

    public FurnaceBurnEvent(BlockEntityFurnace furnace, Item fuel, int burnTime) {
        super(furnace.getBlock());
        this.fuel = fuel;
        this.burnTime = burnTime;
        this.furnace = furnace;
    }

    public BlockEntityFurnace getFurnace() {
        return furnace;
    }

    public Item getFuel() {
        return fuel;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }
}
