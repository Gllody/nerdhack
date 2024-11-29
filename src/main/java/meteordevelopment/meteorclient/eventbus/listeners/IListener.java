package meteordevelopment.meteorclient.eventbus.listeners;

public interface IListener {
    void call(Object event);

    Class<?> getTarget();

    int getPriority();

    boolean isStatic();
}
