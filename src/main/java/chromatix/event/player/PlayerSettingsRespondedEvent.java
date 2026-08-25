package chromatix.event.player;

import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.form.response.Response;
import chromatix.form.window.Form;

public class PlayerSettingsRespondedEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected int formID;
    protected Form<?> window;
    protected Response response;

    public PlayerSettingsRespondedEvent(Player player, int formID, Form<?> window, Response response) {
        this.player = player;
        this.formID = formID;
        this.window = window;
        this.response = response;
    }

    public int getFormID() {
        return this.formID;
    }

    public Form<?> getWindow() {
        return window;
    }

    /**
     * Can be null if player closed the window instead of submitting it
     *
     * @return response
     */
    public Response getResponse() {
        return response;
    }

    @Override
    public void setCancelled() {
        super.setCancelled();
    }

}
