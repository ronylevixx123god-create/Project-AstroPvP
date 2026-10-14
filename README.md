# Download the restored AstroPvP test server

Private server distribution. Keep this repository/branch private. No production data or credentials are included. Commercial plugins still require legitimate authorization.

## Windows: get your files

1. While signed in to GitHub, open this branch, choose **Code > Download ZIP**. Download on your Windows PC.
2. Right-click the downloaded ZIP and choose **Extract All**.
3. Open the extracted folder and double-click **EXTRACT_ASTRO.bat**.
4. It verifies and combines the four parts, then extracts the complete **AstroPvP-Windows-Test** server folder. Explorer opens that folder.

The four parts are necessary because the 137 MB server ZIP exceeds GitHub's 100 MB per-file limit. Do not open the part files individually. The generated extraction helper has not been executed on Windows in this cloud session; its byte concatenation and checksums were validated on Linux.

The helper only combines and extracts files. It does not install Java/MariaDB, accept a new EULA on your PC, or start the server. Use a fresh destination; existing server files are not overwritten.

## Before starting

- Use the original included Banana/TacoSpigot 1.8.8 fork and Minecraft Java Edition 1.8.9.
- Install Java 8 and configure a separate local MariaDB test database. Native Windows instructions do not require Docker. Ask for the next setup step after extraction.
- Existing SETUP_DATABASE.bat / START_SERVER.bat scripts expect Docker; do not use them for native Windows setup.
- Database password placeholders must be configured locally. Do not publish or share your passwords.
- FactionsKore requires legitimate authorization; AstroCore functionality depends on it. DiscordSync requires authentication.
- Original AstroPvP map/spawn/arena builds are not included: only generated test-world metadata. Several gameplay abilities remain unverified; this is not production-ready.
- Source repairs are on the separate **codex/astropvp-restoration** branch. This download branch contains the packaged test server only.
