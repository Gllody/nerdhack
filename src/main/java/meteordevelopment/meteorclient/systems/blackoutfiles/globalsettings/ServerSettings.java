/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.blackoutfiles.globalsettings;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

/**
 * @author OLEPOSSU
 */

public class ServerSettings extends Module {
    public ServerSettings() {
        super(Categories.Player, "Server", "Global server settings for every blackout module.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Boolean> cc = sgGeneral.add(new BoolSetting.Builder()
        .name("CC Hitboxes")
        .description("Newly placed crystals require 1 block tall space without entity hitboxes.")
        .defaultValue(false)
        .build()
    );
    public final Setting<Boolean> oldVerCrystals = sgGeneral.add(new BoolSetting.Builder()
        .name("1.12.2 Crystals")
        .description("Requires 2 block tall space to place crystals.")
        .defaultValue(false)
        .build()
    );
    public final Setting<Boolean> oldVerDamage = sgGeneral.add(new BoolSetting.Builder()
        .name("1.12.2 Damage")
        .description("Calculates damages in old way.")
        .defaultValue(false)
        .build()
    );

}
