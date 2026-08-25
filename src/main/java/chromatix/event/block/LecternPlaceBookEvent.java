package chromatix.event.block;

import chromatix.Player;
import chromatix.blockentity.BlockEntityLectern;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;

public class LecternPlaceBookEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;
    private final BlockEntityLectern lectern;
    private Item book;

    public LecternPlaceBookEvent(Player player, BlockEntityLectern lectern, Item book) {
        super(lectern.getBlock());
        this.player = player;
        this.lectern = lectern;
        this.book = book;
    }

    public BlockEntityLectern getLectern() {
        return lectern;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getBook() {
        return book.clone();
    }

    public void setBook(Item book) {
        this.book = book;
    }
}
