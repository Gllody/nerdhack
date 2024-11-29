/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.blackoutfiles;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public record PlaceData(BlockPos pos, Direction dir, boolean valid) {}
