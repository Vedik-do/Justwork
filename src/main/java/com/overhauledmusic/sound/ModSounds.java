package com.overhauledmusic.sound;

import com.overhauledmusic.OverhauledMusic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    private ModSounds() {}

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OverhauledMusic.MODID);

    // End
    public static final RegistryObject<SoundEvent> END_ALL_BIOMES = register("end_all_biomes");
    public static final RegistryObject<SoundEvent> END_LOW_HP = register("end_low_hp");
    public static final RegistryObject<SoundEvent> ENDER_DRAGON_BOSS = register("ender_dragon_boss");

    // Forest/Plains
    public static final RegistryObject<SoundEvent> FOREST_PLAINS_GROUP = register("forest_plains_group");
    public static final RegistryObject<SoundEvent> FOREST_PLAINS_LOW_HP = register("forest_plains_low_hp");

    // Ocean
    public static final RegistryObject<SoundEvent> OCEAN_GROUP = register("ocean_group");
    public static final RegistryObject<SoundEvent> OCEAN_LOW_HP = register("ocean_low_hp");
    public static final RegistryObject<SoundEvent> OCEAN_MONUMENT = register("ocean_monument");

    // Snow/Ice
    public static final RegistryObject<SoundEvent> SNOW_ICE_GROUP = register("snow_ice_group");
    public static final RegistryObject<SoundEvent> SNOW_ICE_LOW_HP = register("snow_ice_low_hp");

    // Cherry Grove
    public static final RegistryObject<SoundEvent> CHERRY_GROVE = register("cherry_grove");
    public static final RegistryObject<SoundEvent> CHERRY_GROVE_LOW_HP = register("cherry_grove_low_hp");

    // Deep Dark / Warden
    public static final RegistryObject<SoundEvent> DEEP_DARK = register("deep_dark");
    public static final RegistryObject<SoundEvent> WARDEN_AND_DEEP_DARK_LOW_HP = register("warden_and_deep_dark_low_hp");

    // Jungle/Swamp
    public static final RegistryObject<SoundEvent> JUNGLE_SWAMP_GROUP = register("jungle_swamp_group");
    public static final RegistryObject<SoundEvent> JUNGLE_SWAMP_LOW_HP = register("jungle_swamp_low_hp");

    // Desert/Badlands/Savanna
    public static final RegistryObject<SoundEvent> DESERT_BADLANDS_GROUP = register("desert_badlands_group");
    public static final RegistryObject<SoundEvent> DESERT_BADLANDS_LOW_HP = register("desert_badlands_low_hp");

    // Stony
    public static final RegistryObject<SoundEvent> STONY_GROUP = register("stony_group");
    public static final RegistryObject<SoundEvent> STONY_LOW_HP = register("stony_low_hp");

    // Nether
    public static final RegistryObject<SoundEvent> NETHER_ALL_BIOMES = register("nether_all_biomes");
    public static final RegistryObject<SoundEvent> NETHER_LOW_HP_AND_FORTRESS = register("nether_low_hp_and_fortress");
    public static final RegistryObject<SoundEvent> BASTION = register("bastion");

    // Boss
    public static final RegistryObject<SoundEvent> WITHER_BOSS = register("wither_boss");

    // Mansion
    public static final RegistryObject<SoundEvent> WOODLAND_MANSION = register("woodland_mansion");

    // Caves
    public static final RegistryObject<SoundEvent> LUSH_CAVES = register("lush_caves");
    public static final RegistryObject<SoundEvent> LUSH_CAVES_LOW_HP = register("lush_caves_low_hp");
    public static final RegistryObject<SoundEvent> DRIPSTONE_CAVES = register("dripstone_caves");
    public static final RegistryObject<SoundEvent> DRIPSTONE_CAVES_LOW_HP = register("dripstone_caves_low_hp");

    private static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(OverhauledMusic.MODID, id)));
    }

    public static void register(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }
}
