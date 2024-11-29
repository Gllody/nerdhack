/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.hud.elements;

import meteordevelopment.meteorclient.NerdHack;
import meteordevelopment.meteorclient.renderer.GL;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.render.color.Color;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;

/**
 * @author KassuK
 */

public class Icone extends HudElement {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
        .name("Color")
        .description(NerdHack.COLOR)
        .defaultValue(new SettingColor(255, 255, 255, 255))
        .build()
    );
    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("Scale")
        .description("Modify the size of the text.")
        .defaultValue(1.5)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );
    private final Setting<Boolean> logo = sgGeneral.add(new BoolSetting.Builder()
        .name("Logo")
        .description("Renders NerdHack logos.")
        .defaultValue(true)
        .build()
    );
    private final Setting<Double> logoScale = sgGeneral.add(new DoubleSetting.Builder()
        .name("Logo Scale")
        .description("Modify the size of the logo.")
        .defaultValue(1)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );

    private final Identifier LOGO = NerdHack.identifier("textures/nerdhackicon.png");

    public static final HudElementInfo<Icone> INFO = new HudElementInfo<>(Hud.GROUP, "NerdHackWatermark", "The NerdHack watermark.", Icone::new);

    public Icone() {
        super(INFO);
    }

    @Override
    public void render(HudRenderer renderer) {
        setSize(renderer.textWidth(NerdHack.NAME + " v" + NerdHack.VERSION, true) * scale.get() * scale.get(), renderer.textHeight(true) * scale.get() * scale.get());

        String text = NerdHack.NAME + " v" + NerdHack.VERSION;

        renderer.text(text, x, y, color.get(), true, scale.get());

        if (!logo.get()) {return;}
        MatrixStack matrixStack = new MatrixStack();

        GL.bindTexture(LOGO);
        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texQuad(x + renderer.textWidth(NerdHack.NAME + " v" + NerdHack.VERSION) * scale.get() * scale.get(),
            y + renderer.textHeight(true) * scale.get() * scale.get() / 2 - logoScale.get() * 128 / 2,
            logoScale.get() * 128, logoScale.get() * 128, new Color(255, 255, 255, 255));
        Renderer2D.TEXTURE.render(matrixStack);
    }
}
