@echo off
setlocal EnableExtensions EnableDelayedExpansion

echo === OverhauledMusic Forge 1.20.1 Builder (Windows) ===
echo.

REM Always run from the folder this script is in (fixes path issues)
cd /d "%~dp0"

REM Sanity checks
if not exist "build.gradle" (
  echo ERROR: build.gradle not found in this folder.
  echo Make sure you extracted the ZIP and are running build-jar.bat INSIDE the project folder.
  echo This folder should contain: build.gradle, settings.gradle, src\
  echo.
  pause
  exit /b 1
)

REM Gradle check (wrapper is intentionally not used)
where gradle >nul 2>nul
if errorlevel 1 (
  echo ERROR: Gradle was not found.
  echo Install Gradle 8.x and make sure "gradle" is on PATH.
  echo Then re-run this script.
  echo.
  pause
  exit /b 1
)

REM ---------- Pick Java 17 ----------
REM If user already has JAVA_HOME set to a Java 17 install, use it.
set "JAVA_OK="
if defined JAVA_HOME (
  if exist "%JAVA_HOME%\bin\java.exe" (
    for /f "tokens=1,2 delims==" %%A in ('"%JAVA_HOME%\bin\java.exe" -XshowSettings:properties -version 2^>^&1 ^| findstr /i "java.specification.version"') do (
      set "SPEC=%%B"
    )
    set "SPEC=!SPEC: =!"
    if "!SPEC!"=="17" set "JAVA_OK=1"
  )
)

REM Auto-detect common Java 17 locations if JAVA_HOME is missing/incorrect
if not defined JAVA_OK (
  set "CANDIDATE="
  for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-17*") do (
    if exist "%%D\bin\java.exe" set "CANDIDATE=%%D"
  )
  for /d %%D in ("C:\Program Files\Java\jdk-17*") do (
    if exist "%%D\bin\java.exe" set "CANDIDATE=%%D"
  )
  for /d %%D in ("C:\Program Files\BellSoft\LibericaJDK-17*") do (
    if exist "%%D\bin\java.exe" set "CANDIDATE=%%D"
  )
  if defined CANDIDATE (
    set "JAVA_HOME=!CANDIDATE!"
    set "JAVA_OK=1"
  )
)

if not defined JAVA_OK (
  echo ERROR: Java 17 (JDK) was not found.
  echo Install JDK 17, then re-run this file.
  echo Recommended: Temurin 17 (Adoptium) or Oracle JDK 17.
  echo.
  echo After installing, you can also set JAVA_HOME to your JDK 17 folder.
  echo Example:
  echo   setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-17.x.x.x-hotspot"
  echo.
  pause
  exit /b 1
)

REM Force this build to use Java 17 (even if Java 25 exists)
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Using Java from:
echo   %JAVA_HOME%
echo.
java -version
echo.

REM ---------- Build ----------
echo Running Gradle build...
echo (First build can take a few minutes while it downloads Forge/Minecraft dependencies.)
echo.

call gradle clean build
set "RC=%ERRORLEVEL%"

echo.
if "%RC%"=="0" (
  echo BUILD SUCCESSFUL.
  echo Your mod JAR is in: "%~dp0build\libs\"
) else (
  echo BUILD FAILED with exit code %RC%.
  echo Scroll up to see the error. If you want, copy/paste that error here.
)
echo.
pause
exit /b %RC%
