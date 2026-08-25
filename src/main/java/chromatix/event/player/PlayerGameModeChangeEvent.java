package chromatix.event.player;

import chromatix.AdventureSettings;
import chromatix.Player;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;

public class PlayerGameModeChangeEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final int gamemode;
    protected AdventureSettings newAdventureSettings;

    public PlayerGameModeChangeEvent(Player player, int newGameMode, AdventureSettings newAdventureSettings) {
        this.player = player;
        this.gamemode = newGameMode;
        this.newAdventureSettings = newAdventureSettings;
    }

    public int getNewGamemode() {
        return gamemode;
    }

    public AdventureSettings getNewAdventureSettings() {
        return newAdventureSettings;
    }

    public void setNewAdventureSettings(AdventureSettings newAdventureSettings) {
        this.newAdventureSettings = newAdventureSettings;
    }
}
