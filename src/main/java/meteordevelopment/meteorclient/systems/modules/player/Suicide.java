/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.player;

import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.CrystalAura;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.DeathScreen;
import meteordevelopment.meteorclient.systems.modules.Module;

/**
 * @author OLEPOSSU
 */

public class Suicide extends Module {
    public Suicide() {
        super(Categories.Player, "Suicide", "Kills yourself. BETTER DON'T TRY!");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> disableDeath = sgGeneral.add(new BoolSetting.Builder()
        .name("Disable On Death")
        .description("Disables the module on death.")
        .defaultValue(true)
        .build()
    );
    public final Setting<Boolean> enableCA = sgGeneral.add(new BoolSetting.Builder()
        .name("Enable Auto Crystal")
        .description("Enables auto crystal when enabled.")
        .defaultValue(true)
        .build()
    );

    @Override
    public void onActivate() {
        if (enableCA.get() && !Modules.get().isActive(CrystalAura.class)) {
            Modules.get().get(CrystalAura.class).toggle();
        }
    }

    @EventHandler(priority = 6969)
    private void onDeath(OpenScreenEvent event) {
        if (event.screen instanceof DeathScreen && disableDeath.get()) {
            toggle();
        }
    }
}

