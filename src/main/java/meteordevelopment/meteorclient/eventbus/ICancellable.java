package meteordevelopment.meteorclient.eventbus;

public interface ICancellable {
    void setCancelled(boolean cancelled);

    default void cancel() {
        setCancelled(true);
    }

    boolean isCancelled();
}
