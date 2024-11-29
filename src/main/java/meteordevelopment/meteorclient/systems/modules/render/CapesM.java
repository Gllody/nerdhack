/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.render;

import meteordevelopment.meteorclient.NerdHack;
import meteordevelopment.meteorclient.eventbus.EventHandler;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

public class CapesM extends Module {

    public CapesM() {
        super(Categories.Render, "capes", "Gives Player NerdHack Capes");
    }

    public String capeurl = "https://raw.githubusercontent.com/NotGllody/MCapes/main/NerdCape.png";
    public String CDOSCape = "1.2.1.2";
    public String nerdHLoc = "resources/assets/nerd-hack/textures/nerdcape.png";
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Decide from packet or client sided rotation.")
        .defaultValue(Mode.Envy)
        .build()
    );


    private final Setting<Boolean> showCape = sgGeneral.add(new BoolSetting.Builder()
        .name("Developer-Capes")
        .description("Shows Developer Capes")
        .defaultValue(true)
        .build()
    );
    public interface ReturnCapeTexture {
        void response(Identifier id);
    }


    @EventHandler
    public boolean onTick(TickEvent.Post event) {

        if (mode.get() == Mode.Envy) {
            capeurl = "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/EnvyCape.png";
        }
        if (mode.get() == Mode.Optifine) {
            capeurl = "http://s.optifine.net/capes/%s.png";
        }
        if (mode.get() == Mode.NerdHack1) {
            capeurl = "https://raw.githubusercontent.com/NotGllody/MCapes/main/NerdCape.png";
        }
        if (mode.get() == Mode.Volcanware) {
            capeurl = "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/VolcanwareCape.png";
        }
        if (mode.get() == Mode.Toxin) {
            capeurl = "https://raw.githubusercontent.com/Volcanware/Envy-Client/Now-Fixed/ToxinCape.png";
        }

        return false;
    }
    public static final Identifier BLANK = NerdHack.identifier("textures/nerdcape.png");
    @EventHandler
    public boolean onTick(TickEvent.Pre event) {

        if (showCape.get()) {
            try {
            } catch (Exception ignored) {
            }
        }

        return false;
    }
    public enum Mode {
        Envy("NH"),
        Volcanware("TEST"),
        Toxin("TEST"),

        Optifine("Optifine"),
        NerdHack1("NerdHack");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public static NativeImage parseCape(NativeImage image) {
        int imageWidth = 64;
        int imageHeight = 32;
        int imageSrcWidth = image.getWidth();
        int srcHeight = image.getHeight();

        for (int imageSrcHeight = image.getHeight(); imageWidth < imageSrcWidth || imageHeight < imageSrcHeight; imageHeight *= 2) {
            imageWidth *= 2;
        }

        NativeImage imgNew = new NativeImage(imageWidth, imageHeight, true);
        for (int x = 0; x < imageSrcWidth; x++) {
            for (int y = 0; y < srcHeight; y++) {
                imgNew.setColor(x, y, image.getColor(x, y));
            }
        }
        image.close();
        return imgNew;
    }

}

