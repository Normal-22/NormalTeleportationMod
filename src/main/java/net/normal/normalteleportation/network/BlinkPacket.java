package net.normal.normalteleportation.network;

import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class BlinkPacket {
    private final double x, y, z;

    public BlinkPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void encode(BlinkPacket msg, FriendlyByteBuf buffer) {
        buffer.writeDouble(msg.x);
        buffer.writeDouble(msg.y);
        buffer.writeDouble(msg.z);
    }

    public static BlinkPacket decode(FriendlyByteBuf buffer) {
        return new BlinkPacket(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    public static void handle(BlinkPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                player.teleportTo(msg.x, msg.y, msg.z);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
