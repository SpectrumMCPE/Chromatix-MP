package chromatix.event.player;

import chromatix.Player;
import chromatix.dialog.response.FormResponseDialog;
import chromatix.dialog.window.FormWindowDialog;
import chromatix.event.HandlerList;

public class PlayerDialogRespondedEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected FormWindowDialog dialog;

    protected FormResponseDialog response;

    public PlayerDialogRespondedEvent(Player player, FormWindowDialog dialog,FormResponseDialog response) {
        this.dialog = dialog;
        this.response = response;
    }

    public FormWindowDialog getDialog() {
        return dialog;
    }

    public FormResponseDialog getResponse() {
        return response;
    }
}
