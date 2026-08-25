package chromatix.event.player;

import chromatix.Player;
import chromatix.entity.item.EntityFishingHook;
import chromatix.event.Cancellable;
import chromatix.event.HandlerList;
import chromatix.item.Item;
import chromatix.math.Vector3;

/**
 * An event that is called when player catches a fish
 *
 * @author PetteriM1
 */

public class PlayerFishEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final EntityFishingHook hook;
    private Item loot;
    private int experience;
    private Vector3 motion;

    public PlayerFishEvent(Player player, EntityFishingHook hook, Item loot, int experience, Vector3 motion) {
        this.player = player;
        this.hook = hook;
        this.loot = loot;
        this.experience = experience;
        this.motion = motion;
    }

    public EntityFishingHook getHook() {
        return hook;
    }

    public Item getLoot() {
        return loot;
    }

    public void setLoot(Item loot) {
        this.loot = loot;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Vector3 getMotion() {
        return motion;
    }

    public void setMotion(Vector3 motion) {
        this.motion = motion;
    }
}
