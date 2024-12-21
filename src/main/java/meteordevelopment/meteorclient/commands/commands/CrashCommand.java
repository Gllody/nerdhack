package meteordevelopment.meteorclient.commands.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.mixininterface.ISimpleOption;
import net.minecraft.command.CommandSource;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import javax.swing.*;
import java.awt.*;

import javax.swing.*;

public class CrashCommand extends Command {
    public CrashCommand() {
        super("dupe", "dupe items");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            JFrame frame = new JFrame("Crash");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            return SINGLE_SUCCESS;
        });
    }
}
