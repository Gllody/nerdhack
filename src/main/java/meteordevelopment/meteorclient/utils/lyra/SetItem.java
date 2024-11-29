package meteordevelopment.meteorclient.utils.lyra;

import net.minecraft.item.ItemStack;
import static meteordevelopment.meteorclient.NerdHack.mc;

public class SetItem {
    public static void set(ItemStack item, int slot) {
        mc.interactionManager.clickCreativeStack(item, slot);
    }
    public static void setMainHand(ItemStack item) {
        mc.interactionManager.clickCreativeStack(item, 36 + mc.player.getInventory().selectedSlot);
    }
    public static void setOffHand(ItemStack item) {
        mc.interactionManager.clickCreativeStack(item, 45);
    }
}
