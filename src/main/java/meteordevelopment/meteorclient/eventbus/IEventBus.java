package meteordevelopment.meteorclient.eventbus;

import meteordevelopment.meteorclient.eventbus.listeners.IListener;
import meteordevelopment.meteorclient.eventbus.listeners.LambdaListener;

public interface IEventBus {
    void registerLambdaFactory(String packagePrefix, LambdaListener.Factory factory);

    <T> T post(T event);

    <T extends ICancellable> T post(T event);

    void subscribe(Object object);

    void subscribe(Class<?> klass);

    void subscribe(IListener listener);

    void unsubscribe(Object object);

    void unsubscribe(Class<?> klass);

    void unsubscribe(IListener listener);
}
