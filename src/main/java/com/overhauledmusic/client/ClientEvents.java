package com.overhauledmusic.client;

import com.overhauledmusic.OverhauledMusic;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OverhauledMusic.MODID, value = Dist.CLIENT)
public final class ClientEvents {
    private static final MusicDirector DIRECTOR = new MusicDirector();

    private ClientEvents() {}

    /**
     * Kept for future expansion (config loading, etc.). EventBusSubscriber methods already run without extra wiring.
     */
    public static void init() {
        // no-op
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        DIRECTOR.maybeCancelVanillaMusic(event);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        DIRECTOR.onClientTick(event);
    }
}
