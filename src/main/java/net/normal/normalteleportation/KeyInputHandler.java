package net.normal.normalteleportation;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.normal.normalteleportation.network.PacketHandler;
import net.normal.normalteleportation.network.BlinkPacket;
import net.normal.normalteleportation.network.SwitchPacket;

@Mod.EventBusSubscriber(modid = NormalTeleportation.MODID, value = Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;

        if (player != null && player.getCommandSenderWorld().isClientSide) {
            if (KeyBindings.blinkKey.isDown()) {
                System.out.println("Blink key pressed!");
                teleportToLookAt(player);
            }

            if (KeyBindings.switchKey.isDown()) {
                System.out.println("Switch key pressed!");
                switchPlaces(player);
            }
        }
    }

    // Teleportation method
    private static void teleportToLookAt(Player player) {
        System.out.println("Teleporting to block...");
        if (player != null) {
            HitResult result = player.pick(200, 1.0F, false);
            if (result != null && result.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult) result).getBlockPos();
                Vec3 teleportPosition = new Vec3(
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 1.5, // Adjust Y-coordinate to be slightly above the block
                        blockPos.getZ() + 0.5
                );
                PacketHandler.INSTANCE.sendToServer(new BlinkPacket(teleportPosition.x, teleportPosition.y, teleportPosition.z));
                System.out.println("Teleported to: " + teleportPosition);
                playSound(player, ModSounds.BLINK.get());
            } else {
                System.out.println("No block targeted.");
            }
        }
    }

    // Method to switch places with the targeted entity
    private static void switchPlaces(Player player) {
        System.out.println("Switching places...");
        Entity target = getLookedAtEntity(player, 50.0);
        if (target != null) {
            double playerX = player.getX();
            double playerY = player.getY();
            double playerZ = player.getZ();

            PacketHandler.INSTANCE.sendToServer(new SwitchPacket(target.getId()));
            System.out.println("Switched places with entity: " + target.getName().getString());
            playSound(player, ModSounds.SWITCH.get());
        } else {
            System.out.println("No entity targeted.");
        }
    }

    // Method to get the entity the player is looking at within a specified distance
    private static Entity getLookedAtEntity(Player player, double distance) {
        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition(1.0F);
        Vec3 end = start.add(look.scale(distance));

        for (Entity entity : player.getCommandSenderWorld().getEntities(player, player.getBoundingBox().inflate(distance))) {
            if (entity.getBoundingBox().clip(start, end).isPresent()) {
                return entity;
            }
        }
        return null;
    }

    // Method to play a sound at the player's location
    private static void playSound(Player player, net.minecraft.sounds.SoundEvent sound) {
        player.getCommandSenderWorld().playSound(player, player.blockPosition(), sound, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
