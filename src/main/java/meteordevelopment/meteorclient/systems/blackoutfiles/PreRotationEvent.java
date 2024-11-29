/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.blackoutfiles;

public class PreRotationEvent {
    private PreRotationEvent() {
    }

    public static final PreRotationEvent INSTANCE = new PreRotationEvent();
}
