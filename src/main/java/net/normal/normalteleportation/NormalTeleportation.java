package net.normal.normalteleportation;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.normal.normalteleportation.network.PacketHandler;
import org.slf4j.Logger;

@Mod(NormalTeleportation.MODID)
public class NormalTeleportation {
    public static final String MODID = "normalteleportation";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NormalTeleportation() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerKeyMappings);

        ModSounds.SOUND_EVENTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketHandler.registerPackets();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client-side setup code
    }

    private void registerKeyMappings(final RegisterKeyMappingsEvent event) {
        KeyBindings.registerKeyBindings(event);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
