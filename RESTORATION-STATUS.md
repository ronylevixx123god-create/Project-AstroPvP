# AstroPvP restoration status

Isolated validation on 2026-10-14. Production untouched. Original Banana/TacoSpigot 1.8.8 fork, knockback settings and license validation preserved.

- Full Maven reactor: 80 JAR modules / 82 projects built successfully.
- Latest isolated startup: 95 plugins loaded, 92 enabled (test probes excluded). Disabled: FactionsKore awaiting legitimate authorization, AstroCore awaiting its authorized dependency, AstroDiscordSync awaiting authentication.
- CosmicContests saved-entry parser fixed; eight loader assertions passed on initial startup and two restarts. Contest GUI opened. mcMMO itself is absent; its contest is not certified functional.
- Latest AstroBAH live regression: 10/10 passed, including GUI bids, refunds, reward collection, history and real restarts. Previously executed crash and unit suites: 4/4 and 19/19; not rerun in the final parser batch.
- Tournaments: 11 lifecycle/bracket checks passed, including a four-player bracket, three match eliminations and winner/pending-reward persistence. Full original kit effects require authorized AstroCore/FactionsKore; original TournamentLootbox definition and original arena builds are missing. Pending reward entitlements are preserved.
- Against the Odds: new user-approved Legendary sword enchant I/II/III, +3/+6/+10% damage, at least three active opponents within 17 blocks, equal teams qualify, friendly numerical advantage does not. Thirteen targeted checks plus three book/sword level checks passed. Normal official-client combat activation needs further verification.
- Last combat regression: 622 passed, 2 known failures, 1 unverified. Failures are the unchanged fork's stronger-sprint-knockback expectation and Phoenix calling unavailable AstroCore KillTrackerAPI. Continuous airborne physics remains unverified.
- Original inactive enchantments were not enabled: Ice Aspect's commented-out slowness remains untouched.
- No official Minecraft 1.8.9 client or Windows execution was tested. Protocol clients and isolated server fixtures do not certify combat feel.
- No original AstroPvP map was recovered from the repository/history or checked backup locations. Test terrain/metadata is not the original spawn/arena build.

## Build prerequisites

Use Java 8 and the repository's dependency preparation helpers in tools/. Legacy commercial/bundled dependencies must be obtained legitimately. Build AstroFactions before a fresh standalone Contest build; Contest now references the verified rebuilt Factions API rather than the incompatible legacy bundled Factions JAR.

Source changes and generated test-server distribution are published separately. No production data, live credentials, test databases, or generated world chunks are included in this source update. This is not a production-readiness claim.
