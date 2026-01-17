package com.overhauledmusic.client;

import com.overhauledmusic.OverhauledMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;

import java.util.EnumMap;
import java.util.Map;

public final class MusicDirector {
    // Priority order:
    // Boss > Low HP > Mobs > Biome
    private static final int FADE_TICKS = 40;         // ~2 seconds
    private static final int KEEP_ALIVE_TICKS = 600;  // 30 seconds at 20 TPS

    private final Map<Track, FadingMusicInstance> instances = new EnumMap<>(Track.class);
    private Track current = null;

    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        // We cancel vanilla MUSIC in maybeCancelVanillaMusic(PlaySoundEvent).
        // Avoid calling MusicManager internals here to reduce mapping/version breakage.

        Track desired = pickTrack(mc, mc.level, mc.player);
        if (desired == null) return;

        swapTo(mc, desired);
        cleanupExpired(mc);
    }

    public void maybeCancelVanillaMusic(PlaySoundEvent event) {
        SoundInstance s = event.getSound();
        if (s == null) return;

        if (s.getSource() != net.minecraft.sounds.SoundSource.MUSIC) return;

        ResourceLocation id = s.getLocation();
        if (id == null) return;

        // Never cancel our own tracks.
        if (OverhauledMusic.MODID.equals(id.getNamespace())) return;

        // Cancel vanilla background music (minecraft:music.*)
        if ("minecraft".equals(id.getNamespace())) {
            String path = id.getPath();
            if (path.startsWith("music") || path.contains("music.")) {
                event.setSound(null);
            }
        }
    }

    private void swapTo(Minecraft mc, Track desired) {
        if (desired == current) {
            // Ensure current is audible if it existed.
            FadingMusicInstance inst = instances.get(desired);
            if (inst != null) {
                inst.setActive(true);
                inst.fadeTo(1.0f, FADE_TICKS);
            }
            return;
        }

        // Fade out the current track (soft-pause).
        if (current != null) {
            FadingMusicInstance curInst = instances.get(current);
            if (curInst != null) {
                curInst.setActive(false);
                curInst.fadeTo(0.0f, FADE_TICKS);
            }
        }

        // Fade in (or start) the desired track.
        FadingMusicInstance next = instances.get(desired);
        if (next == null || !mc.getSoundManager().isActive(next)) {
            next = new FadingMusicInstance(desired.soundEvent());
            instances.put(desired, next);
            mc.getSoundManager().play(next);
        }
        next.setActive(true);
        next.fadeTo(1.0f, FADE_TICKS);

        current = desired;
    }

    private void cleanupExpired(Minecraft mc) {
        // Stop tracks that have been inactive for long enough.
        for (var it = instances.entrySet().iterator(); it.hasNext(); ) {
            var e = it.next();
            FadingMusicInstance inst = e.getValue();
            if (inst == null) {
                it.remove();
                continue;
            }
            if (!mc.getSoundManager().isActive(inst)) {
                it.remove();
                continue;
            }
            if (!inst.isActiveTrack() && inst.getInactiveTicks() >= KEEP_ALIVE_TICKS) {
                inst.stopSound();
                it.remove();
            }
        }

        // Safety: if current got removed, clear pointer.
        if (current != null && !instances.containsKey(current)) {
            current = null;
        }
    }

    private Track pickTrack(Minecraft mc, ClientLevel level, Player player) {
        BlockPos pos = player.blockPosition();

        // 1) Bosses
        if (isBossNear(player, level, EnderDragon.class, 256)) {
            return Track.ENDER_DRAGON_BOSS;
        }
        if (isBossNear(player, level, WitherBoss.class, 128)) {
            return Track.WITHER_BOSS;
        }

        // 2) Low HP (<= 3 hearts)
        if (player.getHealth() <= 6.0f) {
            return lowHpTrack(level, pos);
        }

        // 3) Mobs (Warden)
        if (isBossNear(player, level, Warden.class, 64)) {
            return Track.WARDEN_AND_DEEP_DARK_LOW_HP;
        }

        // 4) Biome (and dimension)
        return biomeTrack(level, pos);
    }

    private Track lowHpTrack(ClientLevel level, BlockPos pos) {
        // Dimension-based overrides
        ResourceKey<Level> dim = level.dimension();
        if (dim == Level.END) {
            return Track.END_LOW_HP;
        }
        if (dim == Level.NETHER) {
            return Track.NETHER_LOW_HP_AND_FORTRESS;
        }

        ResourceKey<Biome> biome = biomeKey(level, pos);
        if (biome == null) return Track.FOREST_PLAINS_LOW_HP;

        if (biome == BiomeGroups.DEEP_DARK) return Track.WARDEN_AND_DEEP_DARK_LOW_HP;
        if (biome == BiomeGroups.CHERRY_GROVE) return Track.CHERRY_GROVE_LOW_HP;
        if (biome == BiomeGroups.LUSH_CAVES) return Track.LUSH_CAVES_LOW_HP;
        if (biome == BiomeGroups.DRIPSTONE_CAVES) return Track.DRIPSTONE_CAVES_LOW_HP;

        if (BiomeGroups.OCEAN.contains(biome)) return Track.OCEAN_LOW_HP;
        if (BiomeGroups.SNOW_ICE.contains(biome)) return Track.SNOW_ICE_LOW_HP;
        if (BiomeGroups.JUNGLE_SWAMP.contains(biome)) return Track.JUNGLE_SWAMP_LOW_HP;
        if (BiomeGroups.DESERT_BADLANDS.contains(biome)) return Track.DESERT_BADLANDS_LOW_HP;
        if (BiomeGroups.STONY.contains(biome)) return Track.STONY_LOW_HP;

        // Default: forest/plains low
        return Track.FOREST_PLAINS_LOW_HP;
    }

    private Track biomeTrack(ClientLevel level, BlockPos pos) {
        ResourceKey<Level> dim = level.dimension();
        if (dim == Level.END) {
            return Track.END_ALL_BIOMES;
        }
        if (dim == Level.NETHER) {
            return Track.NETHER_ALL_BIOMES;
        }

        ResourceKey<Biome> biome = biomeKey(level, pos);
        if (biome == null) return Track.FOREST_PLAINS_GROUP;

        if (biome == BiomeGroups.DEEP_DARK) return Track.DEEP_DARK;
        if (biome == BiomeGroups.CHERRY_GROVE) return Track.CHERRY_GROVE;
        if (biome == BiomeGroups.LUSH_CAVES) return Track.LUSH_CAVES;
        if (biome == BiomeGroups.DRIPSTONE_CAVES) return Track.DRIPSTONE_CAVES;

        if (BiomeGroups.OCEAN.contains(biome)) return Track.OCEAN_GROUP;
        if (BiomeGroups.SNOW_ICE.contains(biome)) return Track.SNOW_ICE_GROUP;
        if (BiomeGroups.JUNGLE_SWAMP.contains(biome)) return Track.JUNGLE_SWAMP_GROUP;
        if (BiomeGroups.DESERT_BADLANDS.contains(biome)) return Track.DESERT_BADLANDS_GROUP;
        if (BiomeGroups.STONY.contains(biome)) return Track.STONY_GROUP;
        if (BiomeGroups.FOREST_PLAINS.contains(biome)) return Track.FOREST_PLAINS_GROUP;

        // Anything not mapped falls back to forest/plains.
        return Track.FOREST_PLAINS_GROUP;
    }

    // Structure detection was removed for maximum compatibility.
    // Client-side structure APIs and method signatures can differ across mappings/Forge builds,
    // and structure data is often unavailable on many multiplayer servers anyway.

    private static ResourceKey<Biome> biomeKey(ClientLevel level, BlockPos pos) {
        return level.getBiome(pos).unwrapKey().orElse(null);
    }

    private static boolean isBossNear(Player player, ClientLevel level, Class<? extends Entity> cls, double radius) {
        var box = player.getBoundingBox().inflate(radius);
        return !level.getEntitiesOfClass(cls, box, Entity::isAlive).isEmpty();
    }

    // NOTE: If you want structure-based music later, tell me what Forge version you are targeting
    // and I can add a structure check that matches that exact API.
}
