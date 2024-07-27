package net.normal.normalteleportation.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraft.resources.ResourceLocation;
import net.normal.normalteleportation.NormalTeleportation;

public class PacketHandler {
    public static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NormalTeleportation.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, SwitchPacket.class, SwitchPacket::encode, SwitchPacket::decode, SwitchPacket::handle);
        INSTANCE.registerMessage(id++, BlinkPacket.class, BlinkPacket::encode, BlinkPacket::decode, BlinkPacket::handle);
    }
}
