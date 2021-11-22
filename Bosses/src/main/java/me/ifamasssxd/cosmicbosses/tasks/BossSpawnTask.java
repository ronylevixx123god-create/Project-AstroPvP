package me.ifamasssxd.cosmicbosses.tasks;

import java.util.Random;

public class BossSpawnTask implements Runnable {
    Random random = new Random();

    public void run() {
        /*boolean enabled = CosmicBosses.getInstance().isNaturalSpawnEnabled();
        if (!enabled)
            return;
        if (Arrays.<Collection>asList(new Collection[] { Bukkit.getOnlinePlayers() }).size() == 0 || Boss.bosses.size() >= 10)
            return;
        BossType type = null;
        int chance = this.random.nextInt(100);
        if (chance < 40) {
            type = BossType.BROOD_MOTHER;
        } else if (chance < 70) {
            type = BossType.KING_SLIME;
        } else if (chance < 85) {
            type = BossType.SKELETON_HORSE;
        } else if (chance < 94) {
            type = BossType.PLAGUE_BLOATER;
        } else if (chance < 100) {
            type = BossType.DEMONIC_WITHER;
        }
        Location l = BossUtils.getRandomRegionLocation("warzone_spawn_500000");
        if (l == null) {
            Bukkit.getLogger().info("[CosmicBosses] Null location returned by getRandomRegionLocation, skipping BossSpawnTask.");
            return;
        }
        Location highest = l.clone();
        highest.setY((BossUtils.getHighestBlockYAt(highest, false) + 2));
        boolean isSuiteableLocation = isSuitableLocation(highest);
        Bukkit.getLogger().info("[CosmicBosses] getRandomRegionLocation(): " + l + ", goodLocation: " + isSuiteableLocation);
        if (!isSuiteableLocation) {
            Bukkit.getScheduler().runTask((Plugin)CosmicBosses.get(), this);
            return;
        }
        Boss boss = type.createInstance(highest);
        boss.setNaturallySpawned(true);
        boss.onBossSpawn();
    }

    private boolean isSuitableLocation(Location loc) {
        return (!WorldGuardUtils.isPvPDisabled(loc) && !WorldGuardUtils.getRegionName(loc).startsWith("spawn_") && !WorldGuardUtils.getRegionName(loc).contains("warp"));
    }

    private Player getRandomWarzonePlayer() {
        List<Player> players = Lists.newArrayList(Bukkit.getServer().getOnlinePlayers());
        Collections.shuffle(players);
        for (Player retr : players) {
            if (!retr.hasMetadata("NPC") && retr.getLocation().getY() < 256.0D && BossUtils.canAffectPlayer(retr, false) && !WorldGuardUtils.isPvPDisabled(retr.getLocation()) && !WorldGuardUtils.getRegionName(retr.getLocation()).startsWith("spawn_") && !WorldGuardUtils.getRegionName(retr.getLocation()).contains("warp"))
                return retr;
        }
        return null;*/
    }
}
