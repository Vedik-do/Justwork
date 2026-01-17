package com.overhauledmusic.client;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.Set;

final class BiomeGroups {
    private BiomeGroups() {}

    static final Set<ResourceKey<Biome>> FOREST_PLAINS = Set.of(
            Biomes.BIRCH_FOREST,
            Biomes.DARK_FOREST,
            Biomes.FOREST,
            Biomes.GROVE,
            Biomes.MEADOW,
            Biomes.OLD_GROWTH_BIRCH_FOREST,
            Biomes.OLD_GROWTH_PINE_TAIGA,
            Biomes.OLD_GROWTH_SPRUCE_TAIGA,
            Biomes.PLAINS,
            Biomes.SUNFLOWER_PLAINS,
            Biomes.WINDSWEPT_FOREST,
            Biomes.WINDSWEPT_HILLS,
            Biomes.WINDSWEPT_GRAVELLY_HILLS,
            // Extra safe fallbacks (not in your list but common)
            Biomes.TAIGA,
            Biomes.FLOWER_FOREST
    );

    static final Set<ResourceKey<Biome>> OCEAN = Set.of(
            Biomes.BEACH,
            Biomes.RIVER,
            Biomes.DEEP_LUKEWARM_OCEAN,
            Biomes.DEEP_OCEAN,
            Biomes.LUKEWARM_OCEAN,
            Biomes.OCEAN,
            Biomes.WARM_OCEAN
    );

    static final Set<ResourceKey<Biome>> SNOW_ICE = Set.of(
            Biomes.COLD_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN,
            Biomes.DEEP_COLD_OCEAN,
            Biomes.FROZEN_OCEAN,
            Biomes.FROZEN_PEAKS,
            Biomes.FROZEN_RIVER,
            Biomes.ICE_SPIKES,
            Biomes.JAGGED_PEAKS,
            Biomes.SNOWY_BEACH,
            Biomes.SNOWY_PLAINS,
            Biomes.SNOWY_SLOPES,
            Biomes.SNOWY_TAIGA
    );

    static final Set<ResourceKey<Biome>> JUNGLE_SWAMP = Set.of(
            Biomes.SWAMP,
            Biomes.MANGROVE_SWAMP,
            Biomes.MUSHROOM_FIELDS,
            Biomes.JUNGLE,
            Biomes.SPARSE_JUNGLE,
            Biomes.BAMBOO_JUNGLE
    );

    static final Set<ResourceKey<Biome>> DESERT_BADLANDS = Set.of(
            Biomes.DESERT,
            Biomes.BADLANDS,
            Biomes.ERODED_BADLANDS,
            Biomes.WOODED_BADLANDS,
            Biomes.SAVANNA,
            Biomes.SAVANNA_PLATEAU,
            Biomes.WINDSWEPT_SAVANNA
    );

    static final Set<ResourceKey<Biome>> STONY = Set.of(
            Biomes.STONY_PEAKS,
            Biomes.STONY_SHORE
    );

    static final ResourceKey<Biome> CHERRY_GROVE = Biomes.CHERRY_GROVE;
    static final ResourceKey<Biome> DEEP_DARK = Biomes.DEEP_DARK;
    static final ResourceKey<Biome> LUSH_CAVES = Biomes.LUSH_CAVES;
    static final ResourceKey<Biome> DRIPSTONE_CAVES = Biomes.DRIPSTONE_CAVES;
}
