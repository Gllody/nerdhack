/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.eventbus.EventHandler;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.MoveHelper2;

public class VulcanFly extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    double startHeight;

    public VulcanFly() {
        super(Categories.Movement, "VulcanFly", "Vulcan Fly Bypass! Fly is for poor Nerds!");
    }

    private final Setting<Double> clip = sgGeneral.add(new DoubleSetting.Builder()
        .name("clip")
        .description("The clip amount.")
        .defaultValue(10)
        .min(1)
        .sliderRange(1, 100)
        .build()
    );

    // should clip
    private final Setting<Boolean> ShouldClip = sgGeneral.add(new BoolSetting.Builder()
        .name("Clip")
        .description("Should clip.")
        .defaultValue(false)
        .build()
    );

    @Override
    public void onActivate() {

        startHeight = mc.player.getY();
        if (ShouldClip.get()) {
            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + clip.get(), mc.player.getZ());
        }
    }

    @EventHandler
    public void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;

        double clipHeight = startHeight - clip.get();
        //System.out.println("The Player Height is " + mc.player.getY() + "\n And the clip height is " + mc.player.getY());

        if (mc.player.fallDistance > 2) {
            mc.player.setOnGround(true);
            mc.player.fallDistance = 0f;
        }
        if (mc.player.age % 3 == 0) {
            MoveHelper2.motionYPlus(0.026);
        } else {
            MoveHelper2.motionY(-0.0991);
        }
    }
}
