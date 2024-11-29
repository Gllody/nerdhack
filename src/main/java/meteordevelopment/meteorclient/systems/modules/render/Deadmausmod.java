/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.render;

import meteordevelopment.meteorclient.NerdHack;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class Deadmausmod extends Module {
    private PostEffectProcessor shader = null;

    private final SettingGroup sgInvisible = settings.createGroup("Invisible");
    private final SettingGroup sgFun = settings.createGroup("Fun");

    private final Setting<Boolean> structureVoid = sgInvisible.add(new BoolSetting.Builder()
        .name("structure-void")
        .description("Render structure void blocks.")
        .defaultValue(true)
        .onChanged(onChanged -> {
            if (isActive()) mc.worldRenderer.reload();
        })
        .build()
    );

    private final Setting<Boolean> dinnerbone = sgFun.add(new BoolSetting.Builder()
        .name("dinnerbone")
        .description("Applies dinnerbone effects to all entities.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> deadmau5Ears = sgFun.add(new BoolSetting.Builder()
        .name("deadmau5-ears")
        .description("Adds deadmau5 ears to all players.")
        .defaultValue(true)
        .build()
    );

    public Deadmausmod() {
        super(Categories.Render, "Deadmau5", "Deadmau5 Ears and more!");
    }

    @Override
    public void onActivate() {
        mc.worldRenderer.reload();
    }

    @Override
    public void onDeactivate() {
        mc.worldRenderer.reload();
    }

    public void onChanged(Shader s) {
        String name;
        if (s == Shader.Vibrant) name = "color_convolve";
        else if (s == Shader.Scanline) name = "scan_pincushion";
        else name = s.toString().toLowerCase();

        Identifier shaderID = NerdHack.identifier(String.format("shaders/post/%s.json", name));

        try {
            this.shader = new PostEffectProcessor(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shaderID);
        } catch (IOException exception) {
            this.shader = null;
        }
    }

    public boolean renderStructureVoid() {
        return this.isActive() && structureVoid.get();
    }

    public PostEffectProcessor getShaderEffect() {
        if (!isActive()) return null;
        return shader;
    }

    public boolean dinnerboneEnabled() {
        if (!isActive()) return false;
        return dinnerbone.get();
    }

    public boolean deadmau5EarsEnabled() {
        if (!isActive()) return false;
        return deadmau5Ears.get();
    }

    public enum Shader {
        None,
        Notch,
        FXAA,
        Art,
        Bumpy,
        Blobs,
        //i think im gonna add the entire bee movie script here
        Blobs2,
        Pencil,
        Vibrant,
        Deconverge,
        Flip,
        Invert,
        NTSC,
        Outline,
        Phosphor,
        Scanline,
        Sobel,
        Bits,
        Desaturate,
        Green,
        Blur,
        Wobble,
        Antialias,
        Creeper,
        Spider
    }
}
