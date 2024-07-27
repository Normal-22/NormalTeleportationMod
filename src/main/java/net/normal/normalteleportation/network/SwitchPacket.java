package net.normal.normalteleportation.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SwitchPacket {
    private final int entityId;

    public SwitchPacket(int entityId) {
        this.entityId = entityId;
    }

    public static void encode(SwitchPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.entityId);
    }

    public static SwitchPacket decode(FriendlyByteBuf buf) {
        return new SwitchPacket(buf.readInt());
    }

    public static void handle(SwitchPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();
            if (player != null) {
                Entity entity = player.getCommandSenderWorld().getEntity(packet.entityId);
                if (entity != null) {
                    double playerX = player.getX();
                    double playerY = player.getY();
                    double playerZ = player.getZ();

                    player.teleportTo(entity.getX(), entity.getY(), entity.getZ());
                    entity.teleportTo(playerX, playerY, playerZ);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
