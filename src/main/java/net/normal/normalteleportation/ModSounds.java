package net.normal.normalteleportation;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = NormalTeleportation.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NormalTeleportation.MODID);

    public static final RegistryObject<SoundEvent> BLINK = SOUND_EVENTS.register("blink",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(NormalTeleportation.MODID, "blink"), 16.0F));
    public static final RegistryObject<SoundEvent> SWITCH = SOUND_EVENTS.register("switch",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(NormalTeleportation.MODID, "switch"), 16.0F));
}
