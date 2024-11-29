/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.effect.StatusEffects;

/**
 * @author KassuK
 */

public class WeakAlert extends Module {
    public WeakAlert() {
        super(Categories.Combat, "Weakness Alert", "Alerts you if you get weakness.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> single = sgGeneral.add(new BoolSetting.Builder()
        .name("Single")
        .description("Only sends the message once.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("Delay")
        .description("Tick delay between sending the message.")
        .defaultValue(5)
        .range(0, 60)
        .sliderMax(60)
        .visible(() -> !single.get())
        .build()
    );

    private int timer = 0;
    private boolean last = false;

    @EventHandler(priority = EventPriority.HIGH)
    private void onTick(TickEvent.Pre event) {
        if (mc.player != null && mc.world != null) {
            if (mc.player.hasStatusEffect(StatusEffects.WEAKNESS)) {
                if (single.get()) {
                    if (!last) {
                        last = true;
                        info("you have weakness!!!");
                    }
                } else {
                    if (timer > 0) {
                        timer--;
                    } else {
                        timer = delay.get();
                        last = true;
                        info("you have weakness!!!");
                    }
                }
            } else if (last) {
                last = false;
                info("weakness has ended");
            }
        }
    }
}
