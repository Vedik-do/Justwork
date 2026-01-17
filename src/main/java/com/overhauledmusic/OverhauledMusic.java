package com.overhauledmusic;

import com.overhauledmusic.client.ClientEvents;
import com.overhauledmusic.sound.ModSounds;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OverhauledMusic.MODID)
public class OverhauledMusic {
    public static final String MODID = "overhauledmusic";

    public OverhauledMusic() {
        ModSounds.register(FMLJavaModLoadingContext.get().getModEventBus());

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientEvents::init);
    }
}
