@echo off
setlocal EnableExtensions
echo === DEBUG INFO ===
echo Script dir: %~dp0
cd /d "%~dp0"
echo Current dir: %CD%
echo.
echo Files here:
dir /b
echo.
echo Running build-jar.bat now...
echo.
call "%~dp0build-jar.bat"
