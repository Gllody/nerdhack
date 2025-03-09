/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.render;

import meteordevelopment.meteorclient.NerdHack;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

public class CapesM extends Module {

    public static String capeed;


    public CapesM() {
        super(Categories.Render, "capes", "Aetheric Capes");
    }

    private final SettingGroup sgGeneral1 = settings.getDefaultGroup();
    public final Setting<Mode> modee = sgGeneral1.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Decide from packet or client sided rotation.")
        .defaultValue(Mode.Aetheric)
        .build()

    );
    public enum Mode {
        Aetheric("Aetheric"),
        Avo("AVO"),
        Anime1("Anime1"),
        Anime2("Anime2"),
        Anime3("Anime3"),
        Clown("Clown"),
        Developer("DEV"),
        Minecon2011("2011"),
        Minecon2012("2012"),
        Minecon2013("2013"),
        Minecon2015("2015"),
        Minecon2016("2016");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }

    }
    @EventHandler
    public void onActivate() {
        // Przypisanie wartości z ustawienia 'modee' do zmiennej 'cape'
        Mode selectedMode = modee.get(); // Pobranie wybranego trybu raz

        // Teraz sprawdzamy wartość trybu i przypisujemy odpowiednią nazwę kaptura
        switch (selectedMode) {
            case Aetheric:
                capeed = "aetheric";
                break;
            case Avo:
                capeed = "avo";
                break;
            case Anime1:
                capeed = "anime1";
                break;
            case Anime2:
                capeed = "anime2";
                break;
            case Anime3:
                capeed = "anime3";
                break;
            case Clown:
                capeed = "clow";
                break;
            case Developer:
                capeed = "dev";
                break;
            case Minecon2011:
                capeed = "2011";
                break;
            case Minecon2012:
                capeed = "2012";
                break;
            case Minecon2013:
                capeed = "2013";
                break;
            case Minecon2015:
                capeed = "2015";
                break;
            case Minecon2016:
                capeed = "2016";
                break;
            default:
                capeed = "default";
                break;
        }
    }
}

