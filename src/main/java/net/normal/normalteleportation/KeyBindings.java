package net.normal.normalteleportation;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class KeyBindings {
    public static KeyMapping blinkKey;
    public static KeyMapping switchKey;

    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        blinkKey = new KeyMapping("key.normalteleportation.blink", 88, "key.categories.normalteleportation"); // 88 is the keycode for X
        switchKey = new KeyMapping("key.normalteleportation.switch", 67, "key.categories.normalteleportation"); // 67 is the keycode for C
        event.register(blinkKey);
        event.register(switchKey);
    }
}
