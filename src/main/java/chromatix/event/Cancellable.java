package chromatix.event;

/**
 * @author Nukkit Team.
 */
public interface Cancellable {

    boolean isCancelled();

    void setCancelled();

    void setCancelled(boolean forceCancel);
}
