package com.overhauledmusic.client;

import com.overhauledmusic.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

enum Track {
    END_ALL_BIOMES(ModSounds.END_ALL_BIOMES),
    END_LOW_HP(ModSounds.END_LOW_HP),
    ENDER_DRAGON_BOSS(ModSounds.ENDER_DRAGON_BOSS),

    FOREST_PLAINS_GROUP(ModSounds.FOREST_PLAINS_GROUP),
    FOREST_PLAINS_LOW_HP(ModSounds.FOREST_PLAINS_LOW_HP),

    OCEAN_GROUP(ModSounds.OCEAN_GROUP),
    OCEAN_LOW_HP(ModSounds.OCEAN_LOW_HP),
    OCEAN_MONUMENT(ModSounds.OCEAN_MONUMENT),

    SNOW_ICE_GROUP(ModSounds.SNOW_ICE_GROUP),
    SNOW_ICE_LOW_HP(ModSounds.SNOW_ICE_LOW_HP),

    CHERRY_GROVE(ModSounds.CHERRY_GROVE),
    CHERRY_GROVE_LOW_HP(ModSounds.CHERRY_GROVE_LOW_HP),

    DEEP_DARK(ModSounds.DEEP_DARK),
    WARDEN_AND_DEEP_DARK_LOW_HP(ModSounds.WARDEN_AND_DEEP_DARK_LOW_HP),

    JUNGLE_SWAMP_GROUP(ModSounds.JUNGLE_SWAMP_GROUP),
    JUNGLE_SWAMP_LOW_HP(ModSounds.JUNGLE_SWAMP_LOW_HP),

    DESERT_BADLANDS_GROUP(ModSounds.DESERT_BADLANDS_GROUP),
    DESERT_BADLANDS_LOW_HP(ModSounds.DESERT_BADLANDS_LOW_HP),

    STONY_GROUP(ModSounds.STONY_GROUP),
    STONY_LOW_HP(ModSounds.STONY_LOW_HP),

    NETHER_ALL_BIOMES(ModSounds.NETHER_ALL_BIOMES),
    NETHER_LOW_HP_AND_FORTRESS(ModSounds.NETHER_LOW_HP_AND_FORTRESS),
    BASTION(ModSounds.BASTION),

    WITHER_BOSS(ModSounds.WITHER_BOSS),

    WOODLAND_MANSION(ModSounds.WOODLAND_MANSION),

    LUSH_CAVES(ModSounds.LUSH_CAVES),
    LUSH_CAVES_LOW_HP(ModSounds.LUSH_CAVES_LOW_HP),
    DRIPSTONE_CAVES(ModSounds.DRIPSTONE_CAVES),
    DRIPSTONE_CAVES_LOW_HP(ModSounds.DRIPSTONE_CAVES_LOW_HP);

    private final Supplier<SoundEvent> sound;

    Track(Supplier<SoundEvent> sound) {
        this.sound = sound;
    }

    SoundEvent soundEvent() {
        return sound.get();
    }
}
