OverhauledMusic (Forge 1.20.1) - Build Instructions

Why you can't get a prebuilt jar from this zip alone:
- Forge mod builds need Minecraft/Forge dependencies downloaded during the build.

Option A (fastest on your PC):
1) Install Java JDK 17.
2) Double-click build-jar.bat (Windows)
   OR run ./build-jar.sh (Mac/Linux)
3) Grab the jar from build/libs/

Option B (no local setup): GitHub Actions
1) Create a new GitHub repo.
2) Upload/commit this folder.
3) Go to Actions -> 'Build Forge Mod' -> Run workflow.
4) Download the artifact 'OverhauledMusicForge1201-jars' and use the jar inside.
