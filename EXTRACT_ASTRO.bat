@echo off
cd /d "%~dp0"
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0EXTRACT_ASTRO.ps1"
if errorlevel 1 echo Extraction failed. Please share the error message, not passwords.
pause
