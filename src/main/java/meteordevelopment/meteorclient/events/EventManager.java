/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import meteordevelopment.meteorclient.NerdHack;

public final class EventManager
{
    private final NerdHack nerdhacke;
    private final HashMap<Class<? extends Listener>, ArrayList<? extends Listener>> listenerMap =
        new HashMap<>();

    public EventManager(NerdHack wurst)
    {
        this.nerdhacke = wurst;
    }

    /**
     * Fires the given {@link Event} if Wurst is enabled and the
     * {@link EventManager} is ready to accept events. This method is safe to
     * call even when the EventManager hasn't been initialized yet.
     */
    public static <L extends Listener, E extends Event<L>> void fire(E event)
    {
        EventManager eventManager = NerdHack.INSTANCE.getEventManager();
        if(eventManager == null)
            return;

        eventManager.fireImpl(event);
    }

    private <L extends Listener, E extends Event<L>> void fireImpl(E event)
    {
        try
        {
            Class<L> type = event.getListenerType();
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null || listeners.isEmpty())
                return;

            // Creating a copy of the list to avoid concurrent modification
            // issues.
            ArrayList<L> listeners2 = new ArrayList<>(listeners);

            // remove() sets an element to null before removing it. When one
            // thread calls remove() while another calls fire(), it is possible
            // for this list to contain null elements, which need to be filtered
            // out.
            listeners2.removeIf(Objects::isNull);

            event.fire(listeners2);

        }catch(Throwable e)
        {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Firing Wurst event");
            CrashReportSection section = report.addElement("Affected event");
            section.add("Event class", () -> event.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends Listener> void add(Class<L> type, L listener)
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners == null)
            {
                listeners = new ArrayList<>(Arrays.asList(listener));
                listenerMap.put(type, listeners);
                return;
            }

            listeners.add(listener);

        }catch(Throwable e)
        {
            e.printStackTrace();

            CrashReport report =
                CrashReport.create(e, "Adding Wurst event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", () -> type.getName());
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends Listener> void remove(Class<L> type, L listener)
    {
        try
        {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>)listenerMap.get(type);

            if(listeners != null)
                listeners.remove(listener);

        }catch(Throwable e)
        {
            e.printStackTrace();

            CrashReport report =
                CrashReport.create(e, "Removing Wurst event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", () -> type.getName());
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }
}
