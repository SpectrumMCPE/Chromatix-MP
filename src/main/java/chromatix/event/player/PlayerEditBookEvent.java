package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;
import org.cloudburstmc.protocol.bedrock.data.BookEditAction;

public class PlayerEditBookEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Item oldBook;
    private final BookEditAction action;
    private Item newBook;

    public PlayerEditBookEvent(Player player, Item oldBook, Item newBook, BookEditAction action) {
        this.player = player;
        this.oldBook = oldBook;
        this.newBook = newBook;
        this.action = action;
    }

    public BookEditAction getAction() {
        return this.action;
    }

    public Item getOldBook() {
        return this.oldBook;
    }

    public Item getNewBook() {
        return this.newBook;
    }

    public void setNewBook(Item book) {
        this.newBook = book;
    }
}
