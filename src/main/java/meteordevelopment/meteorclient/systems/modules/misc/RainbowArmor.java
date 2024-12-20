package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.PlaySoundEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import meteordevelopment.meteorclient.utils.lyra.SetItem;

public class RainbowArmor extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgExtra = settings.createGroup("Extra");
    private final Setting<RainbowMode> rainbowMODE = sgGeneral.add(new EnumSetting.Builder<RainbowMode>()
        .name("rainbow-mode")
        .description("RGB Method.")
        .defaultValue(RainbowMode.Default)
        .build()
    );
    private final Setting<Integer> speed = sgGeneral.add(new IntSetting.Builder()
        .name("speed")
        .description("WARNING: High speeds might crash the game!")
        .defaultValue(5)
        .min(1)
        .sliderMax(30)
        .build()
    );

    // Head
    private final Setting<Boolean> enableHead = sgGeneral.add(new BoolSetting.Builder()
        .name("head")
        .description("Enable Head.")
        .defaultValue(true)
        .build()
    );
    // Chestplate
    private final Setting<Boolean> enableChestplate = sgGeneral.add(new BoolSetting.Builder()
        .name("chestplate")
        .description("Enable Chestplate.")
        .defaultValue(true)
        .build()
    );
    // Leggings
    private final Setting<Boolean> enableLeggings = sgGeneral.add(new BoolSetting.Builder()
        .name("leggings")
        .description("Enable Leggings.")
        .defaultValue(true)
        .build()
    );
    // Boots
    private final Setting<Boolean> enableBoots = sgGeneral.add(new BoolSetting.Builder()
        .name("boots")
        .description("Enable Boots.")
        .defaultValue(true)
        .build()
    );
    // Extra
    private final Setting<Boolean> toggleOnLog = sgExtra.add(new BoolSetting.Builder()
        .name("toggle-on-log")
        .description("Disables when you disconnect from a server.")
        .defaultValue(true)
        .build()
    );
    private final Setting<Boolean> blockSound = sgExtra.add(new BoolSetting.Builder()
        .name("block-sound")
        .description("Blocks armor equip sound.")
        .defaultValue(true)
        .build()
    );

    public RainbowArmor() {
        super(Categories.Misc, "rainbow-armor", "Gives you Rainbow Leather Armor with various modes.");
    }

    private int linearI;

    @Override
    public void onActivate() {
        if (!mc.player.getAbilities().creativeMode) {
            error("Creative mode only.");
            this.toggle();
        }
        linearI = 0;
    }
    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!toggleOnLog.get()) return;
        toggle();
    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        switch (rainbowMODE.get()) {
            case Default -> setArmor();
            case Linear -> methodLinear();
        }
    }
    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        if (event.sound.getId().toString().equals("minecraft:item.armor.equip_leather") && blockSound.get()) {
            event.cancel();
        }
    }
    private void setArmor() {

        DyedColorComponent dye = new DyedColorComponent(generateRGBMATH(), true);

        if(enableHead.get() && !Modules.get().isActive(CustomHead.class)) {
            InvUtils.swap(36, true);
            ItemStack item = new ItemStack(Items.LEATHER_HELMET);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 5);
        }
        if(enableChestplate.get()) {
            ItemStack item = new ItemStack(Items.LEATHER_CHESTPLATE);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 6);
        }
        if(enableLeggings.get()) {
            ItemStack item = new ItemStack(Items.LEATHER_LEGGINGS);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 7);
        }
        if(enableBoots.get()) {
            ItemStack item = new ItemStack(Items.LEATHER_BOOTS);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 8);
        }
    }
    private void methodLinear() {

        DyedColorComponent dye = new DyedColorComponent(generateRGBMATH(), true);

        if(enableHead.get() && linearI == 0 && !Modules.get().isActive(CustomHead.class)) {
            ItemStack item = new ItemStack(Items.LEATHER_HELMET);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 5);
        }
        if(enableChestplate.get() && linearI == 1) {
            ItemStack item = new ItemStack(Items.LEATHER_CHESTPLATE);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 6);
        }
        if(enableLeggings.get() && linearI == 2) {
            ItemStack item = new ItemStack(Items.LEATHER_LEGGINGS);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 7);
        }
        if(enableBoots.get() && linearI == 3) {
            ItemStack item = new ItemStack(Items.LEATHER_BOOTS);
            item.set(DataComponentTypes.DYED_COLOR, dye);
            SetItem.set(item, 8);
        }
        linearI++;
        if(linearI >= 4) {
            linearI = 0;
        }
    }

    // RGB method
    private int generateRGBMATH() {
        long time = System.currentTimeMillis();
        int red = (int) ((Math.sin(time / (2000.0 / speed.get())) + 1) * 127.5);
        int green = (int) ((Math.sin(time / (2000.0 / speed.get()) + 2 * Math.PI / 3) + 1) * 127.5);
        int blue = (int) ((Math.sin(time / (2000.0 / speed.get()) + 4 * Math.PI / 3) + 1) * 127.5);

        return (red << 16) | (green << 8) | blue;
    }
    public enum RainbowMode {
        Default,
        Linear,
    }
}
