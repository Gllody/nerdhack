/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.mixininterface.IPlayerInteractEntityC2SPacket;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.lang.reflect.Method;

public class MaceKill extends Module {
    private final SettingGroup specialGroup = settings.createGroup("Values higher than 10 only work on Paper/Spigot");
    private final Setting<Integer> fallHeight = specialGroup.add(new IntSetting.Builder()
        .name("Mace Power (Fall height)")
        .description("Simulates a fall from this distance")
        .defaultValue(10)
        .sliderRange(1, 170)
        .min(1)
        .build());
    private final Setting<Boolean> maxPower = specialGroup.add(new BoolSetting.Builder()
        .name("Maximum Mace Power (Paper/Spigot servers only)")
        .description("Simulates a fall from the highest air gap within 170 blocks")
        .defaultValue(false)
        .build());
    private final Setting<Boolean> packetDisable = specialGroup.add(new BoolSetting.Builder()
        .name("Disable When Blocked")
        .description("Does not send movement packets if the attack was blocked. (prevents death)")
        .defaultValue(true)
        .build());

    public MaceKill() {
        super(Categories.Combat, "MaceKill", "Makes the Mace powerful when swung.");
    }

    private Vec3d previouspos;

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (event.packet instanceof IPlayerInteractEntityC2SPacket) {
            IPlayerInteractEntityC2SPacket packet = (IPlayerInteractEntityC2SPacket) event.packet;
            try {
                Class<?> packetClass = packet.getClass();
                Method getTypeMethod = packetClass.getDeclaredMethod("getType");
                getTypeMethod.setAccessible(true);
                Enum<?> interactType = (Enum<?>) getTypeMethod.invoke(packet);

                if (interactType.name().equals("ATTACK") && mc.player.getInventory().getMainHandStack().getItem() == Items.MACE && packet.getEntity() instanceof LivingEntity) {
                    LivingEntity targetEntity = (LivingEntity) packet.getEntity();

                    if (packetDisable.get() && ((targetEntity.isBlocking() && targetEntity.blockedByShield(targetEntity.getRecentDamageSource())) || targetEntity.isInvulnerable() || targetEntity.isInCreativeMode())) return;
                    previouspos = mc.player.getPos();
                    int blocks = getMaxHeightAbovePlayer();

                    int packetsRequired = (int) Math.ceil(Math.abs(blocks / 10));

                    if (packetsRequired > 20) {
                        packetsRequired = 1;
                    }
                    BlockPos isopenair1 = (mc.player.getBlockPos().add(0,blocks,0));
                    BlockPos isopenair2 = (mc.player.getBlockPos().add(0,blocks+1,0));
                    if (isSafeBlock(isopenair1) && isSafeBlock(isopenair2)){
                        if (mc.player.hasVehicle()) {
                            for (int packetNumber = 0; packetNumber < (packetsRequired - 1); packetNumber++) {
                                mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(mc.player.getVehicle()));
                            }
                            mc.player.getVehicle().setPosition(mc.player.getVehicle().getX(), mc.player.getVehicle().getY() + blocks, mc.player.getVehicle().getZ());
                            mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(mc.player.getVehicle()));
                        } else {
                            for (int packetNumber = 0; packetNumber < (packetsRequired - 1); packetNumber++) {
                                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(false));
                            }
                            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + blocks, mc.player.getZ(), false));
                        }

                        // Move back to original position
                        if (mc.player.hasVehicle()) {
                            mc.player.getVehicle().setPosition(previouspos);
                            mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(mc.player.getVehicle()));
                            // Do it again to be sure it happens
                            mc.player.getVehicle().setPosition(previouspos);
                            mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(mc.player.getVehicle()));
                        } else {
                            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(previouspos.getX(), previouspos.getY(), previouspos.getZ(), false));
                            // Do it again to be sure it happens
                            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(previouspos.getX(), previouspos.getY(), previouspos.getZ(), false));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getMaxHeightAbovePlayer() {
        BlockPos playerPos = mc.player.getBlockPos();
        int maxHeight = playerPos.getY() + (maxPower.get() ? 170 : fallHeight.get());

        for (int i = maxHeight; i > playerPos.getY(); i--) {
            BlockPos isopenair1 = new BlockPos(playerPos.getX(), i, playerPos.getZ());
            BlockPos isopenair2 = isopenair1.up(1);
            if (isSafeBlock(isopenair1) && isSafeBlock(isopenair2)) {
                return i - playerPos.getY();
            }
        }
        return 0; // Return 0 if no suitable position is found
    }

    private boolean isSafeBlock(BlockPos pos) {
        return mc.world.getBlockState(pos).isReplaceable()
            && mc.world.getFluidState(pos).isEmpty()
            && !mc.world.getBlockState(pos).isOf(Blocks.POWDER_SNOW);
    }

}
