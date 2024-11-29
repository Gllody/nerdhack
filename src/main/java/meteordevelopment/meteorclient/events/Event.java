/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events;

import java.util.ArrayList;

public abstract class Event<T extends Listener>
{
    public abstract void fire(ArrayList<T> listeners);

    public abstract Class<T> getListenerType();
}
