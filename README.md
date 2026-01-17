# Overhauled Music (Forge 1.20.1)

This is a Forge **1.20.1** mod project that replaces vanilla background music with your custom tracks, using priorities:

**Boss > Low HP (<=3 hearts) > Warden nearby > Structures > Biome**

It also fades between tracks and keeps a just-ended track alive at volume 0 for ~30s so it can **resume seamlessly** if it becomes active again.

## What’s included right now
- **Boss**: Ender Dragon, Wither
- **Low HP**: per-biome/group low HP tracks (plus End/Nether special cases)
- **Mob**: Warden
- **Structures**: Bastion, Nether Fortress, Ocean Monument, Woodland Mansion, Ancient City (village intentionally disabled)
- **Biomes**: your current groups + sensible fallbacks for anything not yet mapped

## Build (on your PC)
1. Install **JDK 17**
2. Install **Gradle** (or generate a wrapper from your IDE)
3. In this folder, run:
   ```
   gradle build
   ```
4. The mod jar will be in:
   `build/libs/OverhauledMusic-1.0.0.jar` (name can vary)

## Install
Drop the built jar into your Minecraft `mods` folder (Forge 1.20.1).

This repo is the **actions-only** mod: it expects the audio + sounds.json to come from a
resource pack named `OverhauledMusic_MusicPack_1.20.1` (or any pack that provides the same
`assets/overhauledmusic/sounds.json` and `.ogg` files). Enable that pack in-game or you'll get silence.

## Next additions (easy)
- Add more mobs for the “near certain mobs” layer (you only provided Warden so far).
- Split cave handling further (e.g., deep caves vs surface) if you want.
