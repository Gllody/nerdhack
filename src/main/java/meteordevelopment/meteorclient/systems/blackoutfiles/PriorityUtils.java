/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.blackoutfiles;

import meteordevelopment.meteorclient.systems.modules.combat.BedAura;
import meteordevelopment.meteorclient.systems.modules.combat.KillAura;
import meteordevelopment.meteorclient.systems.modules.combat.SurroundPlus;
import meteordevelopment.meteorclient.systems.modules.movement.OldScaffold;
import meteordevelopment.meteorclient.systems.modules.player.AutoMend;
import meteordevelopment.meteorclient.systems.modules.player.AutoPearl;

/**
 * @author OLEPOSSU
 */

public class PriorityUtils {
    // Tell me a better way to do this pls
    public static int get(Object module) {

        if (module instanceof AutoMend) return 4;

        if (module instanceof AutoPearl) return 6;
        if (module instanceof BedAura) return 8;
        if (module instanceof KillAura) return 11;
        if (module instanceof OldScaffold) return 2;

        if (module instanceof SurroundPlus) return 0;

        return 100;
    }
}
