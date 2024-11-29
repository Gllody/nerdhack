/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.player;


import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.Settings;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Items;

import static meteordevelopment.meteorclient.NerdHack.mc;

public class AutoPearl extends Module {
    public AutoPearl() {
        super(Categories.Player, "Auto Pearl", "Throws ender pearls! (FAST)");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        FindItemResult franeg = InvUtils.findInHotbar(Items.ENDER_PEARL);
        if (!franeg.found()) return;

        if (franeg.getHand() != null) {
            mc.interactionManager.interactItem(mc.player, franeg.getHand());
        }
        else {
            InvUtils.swap(franeg.slot(), true);
            mc.interactionManager.interactItem(mc.player, franeg.getHand());
            InvUtils.swapBack();
        }

    }
}

