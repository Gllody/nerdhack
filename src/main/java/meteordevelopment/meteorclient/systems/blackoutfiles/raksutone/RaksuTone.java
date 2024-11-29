/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.blackoutfiles.raksutone;

import net.minecraft.util.math.BlockPos;

public class RaksuTone {
    public static RaksuPath getPath(int length, BlockPos target) {
        RaksuPath path = new RaksuPath();
        path.calculate(length, target, false);
        return path;
    }
    public static RaksuPath runAway(int length, BlockPos target) {
        RaksuPath path = new RaksuPath();
        path.calculate(length, target, true);
        return path;
    }
}

