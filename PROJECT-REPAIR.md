# Latest continuation — 2026-10-11

Final status: **80/80 JAR modules compile; 94 plugins loaded, 91 enabled, 3 disabled**. **19 AstroBAH unit tests and 26 scoped live assertions passed**, including actual SIGKILL auction resumption and collected-reward survival. Full tournament equipment/reward delivery is blocked. Production remains untouched; the scoreboard appearance remains unchanged and awaiting approval.

The current detailed report and complete 94-plugin inventory are [CONTINUATION-REPORT.md](/workspace/project-repair-next/CONTINUATION-REPORT.md) and [PLUGIN-INVENTORY.md](/workspace/project-repair-next/PLUGIN-INVENTORY.md). Evidence is in [CONTINUATION-RESULTS.json](/workspace/project-repair-next/CONTINUATION-RESULTS.json). Four AstroBAH baseline source/test files changed intentionally for crash recovery; original test-result evidence remains unchanged and the baseline was archived. Earlier statements below describe the preceding repair stage, not full current readiness.

---

# AstroPvP repair and verification — 2026-10-11

**Current result: 80/80 JAR modules compile; 94 plugins load and 91 enable.** Two additional reactor projects are aggregators, so the complete Maven reactor has 82 successful projects. No source module currently fails compilation. Production was untouched. Counts exclude the two isolated test probes.

The complete server reaches Done, accepts real players, and survives normal restarts, but it is **not fully functional with every required plugin enabled**. FactionsKore cannot complete its license/IP validation in the isolated environment. Core depends on it. Discord authentication is unavailable in these externally blocked tests.

## Verified builds and recovery

- Complete clean build: `/workspace/project-repair-next/full-clean-verified.log` (80 JAR modules, 82 reactor projects). Final source change verification: `final-build.log`.
- The complete saved installation script was actually executed and passed: `install-script-final.log`.
- Original CardboardBox 0.2.2, MiniMessage 4.2.0-SNAPSHOT, Configurate 3.6.1, PlaceholderAPI 2.10.6 and bStats source were recovered. Original source commits and binary checksums are retained in `source-provenance.json` and `tools/recovered-build-dependencies.json`.
- Factions legacy and modern WorldGuard compiler dependencies are separated. Legacy flag queries support the class/interface ABI difference. Shared Utils compiles against the actual Cosmic WorldGuard fork; claim persistence calls the real Board instance.
- Complete ACF includes the original classes and locale files plus real, relocated Guava 19.0. PvPTop privately packages its actual Triumph GUI 3.1.2 dependency.
- Multiverse-Core 4.2.2 was built from original Java 8 source with recovered original library versions. The Phantom test uses its entity type name to support this 1.8.8 runtime. The incompatible Java 11 binary was never deployed.
- ProtocolLib 4.5.0 supplies the CGLIB API expected by original LibsDisguises 8.6.7. Original Citizens, KillTracker, FactionsKore and LuckPerms supporting plugins were located and retained. LuckPerms 5.4.30 uses 19 original runtime libraries verified against checksums embedded in the original plugin.
- Tournaments accepts shipped four-field locations. AstroScoreboard waits for Tournaments. Collectors preserves configured hourly rates by converting them to per-minute rates without truncation; both hourly and minute configuration schemas work.
- Private test SQL configuration supports both GlobalKits schemas and correct Contests/Showcase schemas. No production SQL settings changed.
- All declared system dependency paths exist, including the 77 previously missing references. Both missing Banana dependencies resolve to the original case-correct binary.

## Actual live tests

**AstroBAH: 10/10 passed on the final stack with LuckPerms enabled.** Results: `/workspace/project-full-server/results/b336d9c393-summary.json`. All 52 preserved AstroBAH source/config/test-result files match their previous checksums. Original 10-test result files remain intact in the separate original test environment. The build also runs 16 AstroBAH recovery unit tests successfully.

The live scenarios verify economy hooks, LootTable selection, actual bidding and collection GUI clicks, outbid refunds, SQL history and rewards, pagination, economy-stage changes, persistence across a real restart, and graceful shutdown cancellation/refunds.

**Gameplay integrations: 6/6 passed.** Results: `/workspace/project-full-server/results/d0c7288bc2-gameplay-summary.json`.

- PlayerVaultsX CardboardBox saves and reloads actual named/lore items on Banana 1.8.8.
- Actual player commands create a faction and claim land in a Multiverse world.
- Actual PlayerVaults GUI opens through the player command.
- Actual Tournaments menu opens using registered command and GUI library.
- Vault item metadata, Factions membership/claim, and Multiverse world survive a real restart.
- Persisted PlayerVaults GUI opens after restart.

Initial failures and their underlying logs were retained; corrected final runs above are the reported passes. Claim rejection for insufficient power was genuine; the isolated test fixture then restores player power through the real Factions API. Faction cleanup uses the actual disband confirmation GUI.

## Remaining issues

| Plugin or feature | Current status / cause |
|---|---|
| FactionsKore | Loaded, disabled. The original plugin cannot obtain/validate its license/IP in this isolated environment with external JVM connections blocked. This is not evidence that the production license is invalid. License enforcement was preserved. |
| AstroCore | Loaded, disabled. Its ChatListener requires FactionsKore ShieldFeature. ArmorSets refresh and some Ruins/Core-dependent tasks consequently still fail. |
| AstroDiscordSync | Loaded, disabled. The isolated runner removes Discord authentication and blocks external connections. Live Discord linking was not tested. |
| Tournaments | Enables; shipped locations load; real tournament menu opens. Complete tournament rounds and combat were not tested. |
| AstroScoreboard | Enables and registers TAB placeholders. Its older sidebar renderer remains dormant. TitleManager supplies the live test sidebar. No appearance changes were made. |
| Dungeons, Arcade, Ruins, space and outpost worlds | Test fixtures permit initialization and specific integration checks. Original terrain, schematics, NPC placement, and map-specific objectives were not recovered or comprehensively tested. Flat generated worlds are not original maps. |
| GlobalKit-specific BAH reward grants | Item rewards passed; the earlier unrecovered GlobalKits reward implementation remains outside this proven item-reward coverage. |

The 91 enabled flags are initialization counts, not a claim that every gameplay feature is fully tested. Actual final startup has one enable exception, Core; FactionsKore and Discord disable themselves with explicit prerequisites. External update/telemetry checks are deliberately blocked.

## Scoreboard appearance

Read [the exact configuration, title/colors/lines/layout and limitations](../project-repair-next/SCOREBOARD.md). The colored [preview](../project-repair-next/scoreboard-preview.png) combines the actual TitleManager packets and the preserved dormant AstroPvP source layout. It is not an in-game screenshot; no graphical Minecraft client was used. Production scoreboard configuration was not supplied.

## Complete module inventory

| Module | Compilation |
|---|---|
| AdminItems | Pass |
| AntiRightClick | Pass |
| Arcade | Pass |
| Ark | Pass |
| AstroArmorSets | Pass |
| AstroBAH | Pass |
| AstroCagePvP | Pass |
| AstroChristmas | Pass |
| AstroCollect | Pass |
| AstroCollectors | Pass |
| AstroCrateKeys | Pass |
| AstroCustomBlocks | Pass |
| AstroDamageHandler | Pass |
| AstroDiscordSync | Pass |
| AstroEnvoy | Pass |
| AstroEvents | Pass |
| AstroFactionLogs | Pass |
| AstroFactions/modules/Legacy | Pass |
| AstroFactions/modules/Main | Pass |
| AstroGamble | Pass |
| AstroItemFilter | Pass |
| AstroKits | Pass |
| AstroRevive | Pass |
| AstroScoreboard | Pass |
| AstroSlotBot | Pass |
| AstroSpaceGrinding | Pass |
| AstroSpawnersMobcoins | Pass |
| AstroWildPvP | Pass |
| Attributes | Pass |
| AuctionHouse | Pass |
| Bosses | Pass |
| CCVerify | Pass |
| ChatGames | Pass |
| Conquest | Pass |
| Contest | Pass |
| Core | Pass |
| CosmicShop | Pass |
| CustomBlocks | Pass |
| CustomItems | Pass |
| Disguises | Pass |
| Duels | Pass |
| Dungeon | Pass |
| Enchantments | Pass |
| EnchantmentsAPI | Pass |
| Explosive | Pass |
| FactionBanners | Pass |
| FactionLogs | Pass |
| GiftcardShop | Pass |
| HotFix | Pass |
| HypeBoxes | Pass |
| ItemFlip | Pass |
| ItemSkins | Pass |
| Kits | Pass |
| KoTHs | Pass |
| LPXPatches | Pass |
| LootFilter | Pass |
| LootTable | Pass |
| Lootboxes | Pass |
| Masks | Pass |
| MobCoins | Pass |
| MysteryChests | Pass |
| NebulaShop | Pass |
| Outpost | Pass |
| Pets | Pass |
| PlayerVaultsX | Pass |
| ProxyPatch | Pass |
| PvPTop | Pass |
| Ruins | Pass |
| ShowcaseGUI | Pass |
| Slotbot | Pass |
| SpaceChests | Pass |
| SpaceRecode | Pass |
| StorageUtil | Pass |
| Tournaments | Pass |
| Trials | Pass |
| Utils | Pass |
| Vouchers | Pass |
| WarzoneChests | Pass |
| WeaponRunes | Pass |
| WildPvP | Pass |

## Exact plugin inventory

Two pairs of source modules have duplicate plugin identities: FactionLogs/AstroFactionLogs and AstroWildPvP/WildPvP. Both alternatives compile, but one of each identity is staged because Bukkit cannot load both under the same unchanged name. The originals were not removed or renamed.

| Plugin | Version | Enabled |
|---|---|---|
| Arkkit | 1.0 | Yes |
| AstroAdminItems | 1829.0 | Yes |
| AstroAntiRightClick | 1.2.3 | Yes |
| AstroArcade | 1829.0 | Yes |
| AstroArmorSets | 1.0 | Yes |
| AstroAttributes | 1.0 | Yes |
| AstroCagePvP | 1.0 | Yes |
| AstroChristmas | 1.0 | Yes |
| AstroCollectors | 1.0 | Yes |
| AstroCore | ${project.version} | No |
| AstroCrateKeys | 1.0 | Yes |
| AstroCustomBlocks | 1.0 | Yes |
| AstroDamageHandler | 1.0 | Yes |
| AstroDiscordSync | 1.0-SNAPSHOT | No |
| AstroEnvoys | ${git.total.commit.count}.0 | Yes |
| AstroEvents | 1.0 | Yes |
| AstroGambling | ${git.total.commit.count}.0 | Yes |
| AstroGiftCardPlugin | 1.0 | Yes |
| AstroHypeBoxes | 1829.0 | Yes |
| AstroItemFilter | 1.0 | Yes |
| AstroItemFlip | 1.0 | Yes |
| AstroItemSkins | 1.0 | Yes |
| AstroKillCounters | 1.0 | Yes |
| AstroLootFilter | ${project.version} | Yes |
| AstroLootboxes | 1.0-SNAPSHOT | Yes |
| AstroLoottables | 1829.0 | Yes |
| AstroMobCoins | 1829.0 | Yes |
| AstroPvPTop | 1829.0 | Yes |
| AstroRevive | 1.0 | Yes |
| AstroRuins | 1.0 | Yes |
| AstroScoreboard | 1.0 | Yes |
| AstroShowcase | 1.0 | Yes |
| AstroSlotBot | 1.0-SNAPSHOT | Yes |
| AstroSpaceGrinding | 1.0 | Yes |
| AstroSpawners | 1.0 | Yes |
| AstroTournaments | 1.0 | Yes |
| AstroTrials | 1.0 | Yes |
| AstroWarzoneChests | 1.0 | Yes |
| AstroWeaponBonuses | 1.0 | Yes |
| AstroWildPvP | 1.0-DEV | Yes |
| Auction | 1.1 | Yes |
| BAH | 1.0 | Yes |
| BuycraftX | 12.0.7 | Yes |
| CCVerify | 1.0 | Yes |
| ChatGames | 1.0 | Yes |
| Citizens | 2.0.24-SNAPSHOT (build 1605) | Yes |
| Collect | 1.0 | Yes |
| Commons | 1.0 | Yes |
| CosmicBosses | 0.1 | Yes |
| CosmicConquests | 1.0 | Yes |
| CosmicContests | 1.0 | Yes |
| CosmicCustomTNT | 1.0 | Yes |
| CosmicDisguises | 1.0 | Yes |
| CosmicDuel | 1.0 | Yes |
| CosmicDungeons | 1.0 | Yes |
| CosmicFactionBanners | 1.0 | Yes |
| CosmicGUIShop | 1.0 | Yes |
| CosmicGlobalKits | 1.0 | Yes |
| CosmicKOTH | 1.0 | Yes |
| CosmicKillTracker | 1.0 | Yes |
| CosmicMasks | 1.1 | Yes |
| CosmicMysteryChest | 1.0-SNAPSHOT | Yes |
| CosmicPvPUtils | 1.0 | Yes |
| CosmicSkyCustomItems | 1.0 | Yes |
| CosmicSkyInventoryPets | cosmicpvp-1.0-SNAPSHOT | Yes |
| CosmicSpaceChests | 1.0 | Yes |
| CustomSkullBlocks | ${project.version} | Yes |
| EnchantmentAPI | 4.2 | Yes |
| Enchantments | 1.1 | Yes |
| Essentials | 2.18.2.0 | Yes |
| FactionLogs | 1.0 | Yes |
| Factions | 1.6.9.5-U0.5.18 | Yes |
| FactionsKore | 1.0 | No |
| HolographicDisplays | 2.4.9 | Yes |
| HotFix | 1.0 | Yes |
| Kotlin | 1.3.72 | Yes |
| LPXPatches | 1829.0 | Yes |
| LibsDisguises | 8.6.7 | Yes |
| LuckPerms | 5.4.30 | Yes |
| Multiverse-Core | 4.2.2-recovered | Yes |
| NBTAPI | 2.7.1 | Yes |
| NebulaShop | 1.0 | Yes |
| OutpostPatches | 1.0 | Yes |
| PlaceholderAPI | 2.10.6 | Yes |
| PlayerVaults | 1.0 | Yes |
| ProtocolLib | 4.5.0 | Yes |
| ProxyPatch | 1.0 | Yes |
| SpaceRecode | 1.0 | Yes |
| StorageUtil | 1.1 | Yes |
| TAB | 3.1.2 | Yes |
| TitleManager | 2.3.1 | Yes |
| Vault | 1.7.3-b131 | Yes |
| WorldEdit | 6.1.8-SNAPSHOT;cd4729f | Yes |
| WorldGuard | cosmicpvp-5.9 | Yes |

## Reuse

Use the existing checkout; do not create a worktree. The tested installer is `/workspace/tooling/install-astro-complete.sh`, followed by `python3 tools/verify-luckperms-cache.py`. Recovered dependencies are retained in the cloud recovery cache and checked by manifests. These caches must be retained or recovered from the documented original sources; this is not a claim that a fresh Git clone without the cache can download every historical repository artifact.

Start the owned local database using `bash /workspace/astro-test-server/start-db.sh`. Stage only the isolated server using `/workspace/project-repair/stage-full-server.py`. Run `run-startup.py`, `run-live-tests.py`, and `run-gameplay-tests.py` in `/workspace/project-full-server` sequentially. Each runner starts and stops its owned loopback server; result JSON and logs are retained.

Reusable installation and startup instructions were saved to the cloud configuration draft for review and publication. Saving does not publish the environment or solve the external license/authentication prerequisites.
