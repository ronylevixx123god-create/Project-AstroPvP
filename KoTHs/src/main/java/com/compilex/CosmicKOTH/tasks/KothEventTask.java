package com.compilex.CosmicKOTH.tasks;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.struct.KothWorldType;
import com.compilex.CosmicKOTH.utils.*;
import com.cosmicpvp.cosmicutils.itembuilder.CustomTitleBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import lombok.SneakyThrows;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.RandomFireworks;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class KothEventTask
implements Runnable {
    public final String KOTH_ARENA_REGION = "koth_arena";
    public final String KOTH_ARENA_CAPPING_REGION = "koth_center";
    public final String METADATA_KOTH_CAPPING_START = "KOTH_CAPPING_START";
    Field fireworkField;
    private boolean capped = false;
    private int taskId = -1;
    private Player playerCapping;
    private Player playerCapped = null;
    private int runtime;

    public KothEventTask() {
        try {
            this.fireworkField = EntityFireworks.class.getDeclaredField("ticksFlown");
            this.fireworkField.setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Bukkit.getScheduler().runTask(CosmicKOTHPlugin.get_(), new Runnable(){

            @Override
            public void run() {
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(CC.translate("&c&l*&6&l*&e&l* &f&lA KOTH EVENT HAS STARTED &e&l*&6&l*&c&l*"));
                KothWorldType worldType = KothWorldType.getFromIndex(RewardUtils.getMapIndex());
                if (worldType != null) {
                    Bukkit.broadcastMessage(CC.GreenB + "          Arena: " + CC.Aqua + worldType.getName(true));
                }
                Bukkit.broadcastMessage(ChatColor.GRAY + "Use /koth to view event information!");
                Bukkit.broadcastMessage("");
                if (worldType == KothWorldType.SANTA_WORKSHOP) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather world_koth storm");
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather world_koth sun");
                }
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.2f, 1.6f);
                }
            }
        });
    }

    @SneakyThrows
    @Override
    public void run() {
        if (!this.capped) {
            ++this.runtime;
            try {
                this.playerCapHandler();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            this.advertHandler();
            this.monsterSpawnHandler();
        }
        this.scoreboardHandler();
    }

    private void advertHandler() {
        if (this.runtime % 300 == 0) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(CC.translate("&c&l*&6&l*&e&l* &f&lA KOTH EVENT IS RUNNING &e&l*&6&l*&c&l*"));
            Bukkit.broadcastMessage(ChatColor.GRAY + "Use " + ChatColor.LIGHT_PURPLE + "/koth" + ChatColor.GRAY + " to view event information!");
            Bukkit.broadcastMessage("");
        }
    }

    private void launchKOTHFirework(Location l) {
        Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.AQUA).withFade(Color.PURPLE).with(FireworkEffect.Type.CREEPER).trail(true).build();
        fwm.addEffect(effect);
        fwm.setPower(0);
        fw.setFireworkMeta(fwm);
        CraftFirework craftFire = (CraftFirework)fw;
        try {
            this.fireworkField.set(craftFire.getHandle(), 1);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void scoreboardHandler() {
        for (Player p : this.getKothPlayers()) {
            try {
                ScoreboardUtils.updateScoreboard(p);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playerCapHandler() throws Exception {
        if (this.playerCapping != null && this.playerCapping.isValid() && !this.playerCapping.isDead()) {
            Player playerCapping = this.playerCapping;
            this.getClass();
            if (playerCapping.hasMetadata("KOTH_CAPPING_START")) {
                String regionName;
                String region = regionName = WorldGuardUtils.getRegionName(this.playerCapping.getLocation());
                this.getClass();
                if (!regionName.equals("koth_center")) {
                    this.kothBroadcast(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + ChatColor.BOLD + this.getNameOfFlagCapper() + ChatColor.RED + " is no longer capping KOTH!");
                    de.inventivegames.particle.ParticleEffect.MAGIC_CRIT.sendToPlayers(Bukkit.getOnlinePlayers(),this.playerCapping.getLocation(), 0,1,0,0.6f,30);
                    this.playerCapping.playSound(this.playerCapping.getLocation(), Sound.ANVIL_BREAK, 1.0f, 0.7f);
                    this.playerCapping = null;
                    return;
                }
                int secondsLeft = this.getSecondsUntilCap();
                if (secondsLeft >= 300 && secondsLeft % 300 == 0) {
                    this.kothBroadcast(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + ChatColor.BOLD + this.getNameOfFlagCapper() + ChatColor.GREEN + " is capping KOTH! " + ChatColor.GRAY + "[" + secondsLeft / 60 + "m 0s left]");
                }
                if (secondsLeft < 300 && secondsLeft > 30 && secondsLeft % 60 == 0) {
                    this.kothBroadcast(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + ChatColor.BOLD + this.getNameOfFlagCapper() + ChatColor.GREEN + " is capping KOTH! " + ChatColor.GRAY + "[" + secondsLeft / 60 + "m 0s left]");
                }
                if (secondsLeft == 30 || secondsLeft <= 10) {
                    this.kothBroadcast(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + ChatColor.BOLD + this.getNameOfFlagCapper() + ChatColor.GREEN + " is capping KOTH! " + ChatColor.GRAY + "[0m " + secondsLeft + "s left]");
                }
                if (secondsLeft <= 0) {
                    this.captureKOTH(this.playerCapping);
                }
                return;
            }
        }
        this.playerCapping = null;
        Player pClosest = null;
        double lowestDistance = 2.147483647E9;
        for (Player p : this.getKothPlayers()) {
            String regionName2 = WorldGuardUtils.getRegionName(p.getLocation());
            this.getClass();
            if (!regionName2.equals("koth_center") || CosmicKOTHPlugin.get_().getEss3() != null && CosmicKOTHPlugin.get_().getEss3().getUser(p).isVanished() || p.getGameMode() != GameMode.SURVIVAL) continue;
            double dist = p.getLocation().distanceSquared(CosmicKOTHPlugin.get_().getKothCenter());
            if (pClosest != null && dist >= lowestDistance) continue;
            pClosest = p;
            lowestDistance = dist;
        }
        if (pClosest != null) {
            this.setPlayerCapping(pClosest);
        }
    }

    public void captureKOTH(Player player) {
        FPlayer fplayer;
        this.capped = true;
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(CC.translate("&c&l*&6&l*&e&l* &f&lTHE KOTH HAS BEEN CAPTURED BY: &d&n" + this.getNameOfFlagCapper() + "&r &e&l*&6&l*&c&l*"));
        Bukkit.broadcastMessage(ChatColor.GRAY.toString() + "A " + ChatColor.UNDERLINE + "AstroPvP KOTH Lootbag" + ChatColor.GRAY + " has been added to " + player.getName() + "'s inventory. Prevent them from escaping the /warp koth arena alive to steal the rewards!");
        Bukkit.broadcastMessage("");
        this.logWinner(player);
        this.playerCapped = player;
        this.playerCapping = null;
        Location l = this.playerCapped.getEyeLocation();
        for (int i = 0; i < 10; ++i) {
            Bukkit.getScheduler().runTaskLater(CosmicKOTHPlugin.get_(), () -> RandomFireworks.getManager().launchRandomFirework(l), 20 * i);
        }
        if (this.playerCapped.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("KothBag").createClaimableItem(false));
        } else {
            this.kothBroadcast("");
            this.kothBroadcast(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + this.playerCapped.getName() + " did not have any inventory space, so the Astro Koth Lootbag has been DROPPED!");
            this.kothBroadcast("");
            Item j = this.playerCapped.getWorld().dropItem(player.getLocation(), CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("KothBag").createClaimableItem(false));
            j.setMetadata("kothLootbag", new FixedMetadataValue(CosmicKOTHPlugin.get_(), true));
        }
        if (ArkPlugins.COMBAT_LOG.getArkPlugin().isRunning()) {
            CombatLog.flagCombat(this.playerCapped);
        }
        if ((fplayer = FPlayers.i.get(this.playerCapped)) != null && fplayer.getFaction().isNormal()) {
            FactionPointUtils.giveFactionPoints(fplayer.getFaction(), this.playerCapped, "koth", null);
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fquests add KOTH_CAPTURER " + this.playerCapped.getName());
        if (KothItemUtils.isArmorSetsEnabled()) {
            if (CosmicKOTHPlugin.get_().getKothWorldType() == KothWorldType.HALLOWEEN_NIGHTMARE) {
                try {
                    ItemStack reward = null;
                    int rand = ThreadLocalRandom.current().nextInt(4);
                    Object object = rand == 0 ? AbilityArmorSet.GHOST.getArmorSetInfo().getHelmet(this.playerCapped, true, true) : (rand == 1 ? AbilityArmorSet.GHOST.getArmorSetInfo().getChestplate(this.playerCapped, true, true) : (rand == 2 ? AbilityArmorSet.GHOST.getArmorSetInfo().getLeggings(this.playerCapped, true, true) : (reward = rand == 3 ? AbilityArmorSet.GHOST.getArmorSetInfo().getBoots(this.playerCapped, true, true) : null)));
                    if (reward != null) {
                        PlayerUtil.giveOrDropProtectedItem(this.playerCapped, reward, 120);
                        JSONMessage message = new JSONMessage(CC.Red + this.playerCapped.getName() + " has received a ");
                        message.addItem(reward, ItemUtils.getDisplayName(reward), ChatColor.DARK_AQUA);
                        message.addText(null, CC.Red + " (" + CC.RedB + "SRC: " + CC.Red + "Halloween KOTH)", ChatColor.RED);
                        this.kothBroadcast(message);
                    }
                }
                catch (Exception rand) {
                    // empty catch block
                }
                ItemStack mask = new MaskBuilder("PUMPKIN_MONSTER").build();
                if (mask != null) {
                    PlayerUtil.giveOrDropProtectedItem(this.playerCapped, mask, 120);
                    this.kothBroadcast(CC.Red + this.playerCapped.getName() + " has received a " + ItemUtils.getDisplayName(mask) + CC.Red + " (" + CC.RedB + "SRC: " + CC.Red + "Halloween KOTH)");
                }
            } else if (CosmicKOTHPlugin.get_().getKothWorldType() == KothWorldType.SANTA_WORKSHOP) {
                ItemStack armorset = null;
                int rand2;
                ItemStack mask2;
                ArrayList<ItemStack> items = Lists.newArrayList();
                if (Math.random() <= 0.07) {
                    items.add(new CustomTitleBuilder("#StackinForSanta").build());
                }
                if ((mask2 = new MaskBuilder(Math.random() <= 0.5 ? "SANTA" : "REINDEER").build()) != null) {
                    items.add(mask2);
                }
                Object object = (rand2 = ThreadLocalRandom.current().nextInt(4)) == 0 ? AbilityArmorSet.DEATH_KNIGHT.getArmorSetInfo().getHelmet(this.playerCapped, true, true) : (rand2 == 1 ? AbilityArmorSet.DEATH_KNIGHT.getArmorSetInfo().getChestplate(this.playerCapped, true, true) : (rand2 == 2 ? AbilityArmorSet.DEATH_KNIGHT.getArmorSetInfo().getLeggings(this.playerCapped, true, true) : (armorset = rand2 == 3 ? AbilityArmorSet.DEATH_KNIGHT.getArmorSetInfo().getBoots(this.playerCapped, true, true) : null)));
                if (armorset != null) {
                    items.add(armorset);
                }
                for (ItemStack reward2 : items) {
                    PlayerUtil.giveOrDropProtectedItem(this.playerCapped, reward2, 120);
                    JSONMessage message2 = new JSONMessage(CC.Red + this.playerCapped.getName() + " has received a ");
                    message2.addItem(reward2, ItemUtils.getDisplayName(reward2), ChatColor.DARK_AQUA);
                    message2.addText(null, CC.Red + " (" + CC.RedB + "SRC: " + CC.Red + "Christmas KOTH)", ChatColor.RED);
                    this.kothBroadcast(message2);
                }
            }
        }
        if (Math.random() <= 0.5 && KothItemUtils.isArmorSetsEnabled()) {
            ItemStack reward3 = null;
            ItemStack itemStack = Math.random() <= 0.25 ? (Math.random() <= 0.5 ? KothItemUtils.getKothSwordPvP(this.playerCapped.getName()) : KothItemUtils.getKothAxe(this.playerCapped.getName())) : (reward3 = RewardUtils.getKothArmorItem(this.playerCapped.getName()));
            if (reward3 != null) {
                PlayerUtil.giveOrDropProtectedItem(this.playerCapped, reward3, 120);
                this.kothBroadcast(CC.Red + this.playerCapped.getName() + " has received a " + ItemUtils.getDisplayName(reward3) + CC.Red + " (" + CC.RedB + "SRC: " + CC.Red + "Capturing KOTH)");
            }
        }
    }

    public void logWinner(Player p) {
        try {
            String faction;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            FPlayer fp = FPlayers.i.get(p);
            String facId = fp.getFaction().isNormal() ? "id=" + fp.getFactionId() : "";
            String string = faction = fp.getFaction().isNormal() ? fp.getFaction().getTag() : "N/A";
            if (!new File("plugins/CosmicKOTH/koth.log").exists()) {
                new File("plugins/CosmicKOTH").mkdir();
                new File("plugins/CosmicKOTH/koth.log").createNewFile();
            }
            Files.write(Paths.get("plugins/CosmicKOTH/koth.log"), ("\n[" + dateFormat.format(date) + "] <" + faction + "> " + p.getName() + " (" + p.getUniqueId().toString() + ") at " + CosmicKOTHPlugin.get_().getKoth().getRuntime() + facId).getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void monsterSpawnHandler() {
        if (this.runtime % 240 == 0) {
            int i;
            int multi = this.runtime / 240;
            int toSpawn = Math.min(35, 20 + 2 * multi);
            toSpawn = i = toSpawn / 2;
            /*while (i > 0) {
                LivingEntity ent = MonsterUtils.spawnEnvoyMonster(LocationUtils.getNearbyLocation(CosmicKOTHPlugin.get_().getKothCenter(), 20, 2, 6));
                int nearby = 0;
                for (Entity entity : ent.getNearbyEntities(4.0, 4.0, 4.0)) {
                    if (entity.getType() != ent.getType() || ++nearby < 100) continue;
                    ent.remove();
                    break;
                }
                --i;
            }*/
            this.kothBroadcast("");
            this.kothBroadcast(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** KOTH MONSTERS HAVE SPAWNED! ***");
            this.kothBroadcast(ChatColor.GRAY + "The longer the KOTH lasts, the more minions spawn!");
            this.kothBroadcast("");
        }
    }

    public String getNameOfFlagCapper() {
        if (this.playerCapping == null && this.playerCapped == null) {
            return "???";
        }
        FPlayer fp = FPlayers.i.get(this.playerCapping != null ? this.playerCapping : this.playerCapped);
        if (fp.getFaction().isNormal()) {
            return "[" + fp.getFaction().getTag() + "] " + (this.playerCapping != null ? this.playerCapping.getName() : this.playerCapped.getName());
        }
        return this.playerCapping != null ? this.playerCapping.getName() : this.playerCapped.getName();
    }

    public int getSecondsUntilCap() {
        if (this.playerCapping != null) {
            Player playerCapping = this.playerCapping;
            this.getClass();
            if (playerCapping.hasMetadata("KOTH_CAPPING_START")) {
                Player playerCapping2 = this.playerCapping;
                this.getClass();
                long capStart = playerCapping2.getMetadata("KOTH_CAPPING_START").get(0).asLong();
                long msLeft = 300000L - (System.currentTimeMillis() - capStart);
                int secondsLeft = (int)(msLeft / 1000L);
                return secondsLeft;
            }
        }
        return -1;
    }

    public void setPlayerCapping(Player p) {
        Player playerCapping = this.playerCapping = p;
        this.getClass();
        playerCapping.setMetadata("KOTH_CAPPING_START", new FixedMetadataValue(CosmicKOTHPlugin.get_(), System.currentTimeMillis()));
        this.kothBroadcast(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + ChatColor.BOLD + this.getNameOfFlagCapper() + ChatColor.GREEN + " is capping KOTH! " + ChatColor.GRAY + "[5m 0s left]");
    }

    public void kothBroadcast(String msg) {
        for (Player p : this.getKothPlayers()) {
            p.sendMessage(msg);
        }
    }

    public void kothBroadcast(JSONMessage message) {
        for (Player p : this.getKothPlayers()) {
            message.sendToPlayer(p);
        }
    }

    public List<Player> getKothPlayers() {
        return CosmicKOTHPlugin.get_().getWorld().getPlayers();
    }

    public boolean isCapped() {
        return this.capped;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public Player getPlayerCapping() {
        return this.playerCapping;
    }

    public Player getPlayerCapped() {
        return this.playerCapped;
    }

    public int getRuntime() {
        return this.runtime;
    }
}

