/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.MaskUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicOutposts.outpost.tasks;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.outpost.libs.FactionMemberCounts;
import com.compilex.CosmicOutposts.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CapTask
implements Runnable {
    private float capPercent;
    private Faction attackingFaction = null;
    private Faction defendingFaction = null;
    private Outpost outpost;
    private int iterations = 0;
    private final float capMod = 1.0f;
    private static HashMap<String, Long> factionNotifyTimes = new HashMap();
    private boolean webLink = false;
    private boolean factionUpgrades = false;
    private boolean masks = false;

    public CapTask(Outpost outpost) {
        this.outpost = outpost;
        this.factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        this.webLink = Bukkit.getPluginManager().isPluginEnabled("CosmicWebLink");
        this.masks = new File("plugins/CosmicMasks.jar").exists();
    }

    @Override
    public void run() {
        ++this.iterations;
        List<Player> playersInCapzone = this.getPlayersInCapZone();
        if (this.defendingFaction != null && this.attackingFaction == null && (double)this.capPercent >= 0.1 && this.defendingFaction.getFPlayers().size() > 0 && Bukkit.getPluginManager().isPluginEnabled("CosmicFactionQuests")) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests add HOLD_" + this.outpost.getOutpostTier().name() + "_OUTPOST_I " + this.defendingFaction.getTag()));
        }
        if (playersInCapzone.size() == 0) {
            return;
        }
        if (this.attackingFaction != null && !this.attackingFaction.attached()) {
            this.attackingFaction = null;
        }
        if (this.defendingFaction != null && !this.defendingFaction.attached()) {
            this.defendingFaction = null;
        }
        if (this.outpost.getControllingFaction() != null && Factions.i.getByTag(this.outpost.getControllingFaction()) == null) {
            this.outpost.setControllingFaction(null);
            if (this.defendingFaction != null && this.attackingFaction != null && this.attackingFaction.equals((Object)this.defendingFaction)) {
                this.attackingFaction = null;
            }
            this.defendingFaction = null;
            this.capPercent = 0.0f;
        }
        if (this.attackingFaction == null) {
            boolean strongestFactionHasMask;
            if (playersInCapzone.size() <= 0) {
                return;
            }
            FactionMemberCounts FMC = this.getFactionMemberCounts(playersInCapzone);
            String strongestFaction = this.getStrongestFaction(FMC);
            if (strongestFaction == null) {
                return;
            }
            boolean bl = strongestFactionHasMask = this.masks && this.doesPlayerInStrongestFactionHaveOutpostMask(strongestFaction, playersInCapzone);
            if (this.defendingFaction != null && this.defendingFaction.getTag().equals(strongestFaction) && FMC.size() == 1) {
                if (this.capPercent < 1.0f) {
                    float capPercentIncreaseInterval = this.getCapPercentIncreaseInterval(FMC);
                    this.getClass();
                    float f = capPercentIncreaseInterval * 1.0f;
                    if (this.factionUpgrades) {
                        f = this.applyFactionUpgrades(this.defendingFaction, f);
                    }
                    if (strongestFactionHasMask) {
                        f = (float)((double)f * 2.0);
                    }
                    this.capPercent += f;
                }
                this.attackingFaction = this.outpost.getControllingFaction() == null ? this.defendingFaction : null;
                this.capPercent = this.roundCapPercent();
                return;
            }
            this.attackingFaction = Factions.i.getByTag(strongestFaction);
            if (this.outpost.getControllingFaction() == null && this.doesFactionControlAnOutpost(this.attackingFaction.getTag())) {
                this.notifyFactionOfOutpostAlreadyClaimed(this.attackingFaction);
                this.attackingFaction = null;
                return;
            }
            if (this.defendingFaction != null) {
                if (this.outpost.getCapStatus().contains("BLOCKED")) {
                    this.outpost.broadcastOutpostMessage("&7&l(!) &7&n" + this.defendingFaction.getTag() + "&7's cap of &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost&7 is blocked!&7 [&7" + this.roundCapPercentDisplay() + "%&7]");
                } else if (!this.outpost.getCapStatus().contains("CONTESTED")) {
                    this.outpost.broadcastOutpostMessage("&c&l(!) &c&n" + this.defendingFaction.getTag() + "&c is losing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&c" + this.roundCapPercentDisplay() + "%&7]");
                }
            } else {
                this.defendingFaction = this.attackingFaction;
                this.outpost.broadcastOutpostMessage("&e&l(!) &e&n" + this.attackingFaction.getTag() + "&e is capturing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&e" + this.roundCapPercentDisplay() + "%&7]");
            }
        } else {
            boolean strongestFactionHasMask;
            FactionMemberCounts FMC = this.getFactionMemberCounts(playersInCapzone);
            String strongestFaction = this.getStrongestFaction(FMC);
            if (strongestFaction == null) {
                return;
            }
            boolean bl = strongestFactionHasMask = this.masks && this.doesPlayerInStrongestFactionHaveOutpostMask(strongestFaction, playersInCapzone);
            if (this.isAFactionAttacking() && this.attackingFaction.getTag().equals(strongestFaction)) {
                if (this.isAFactionDefending() && !this.attackingFaction.equals((Object)this.defendingFaction)) {
                    float capPercentDecreaseInterval = this.getCapPercentDecreaseInterval(FMC);
                    this.getClass();
                    float f = capPercentDecreaseInterval * 1.0f;
                    if (this.factionUpgrades) {
                        f = this.applyFactionUpgrades(this.defendingFaction, f);
                    }
                    if (strongestFactionHasMask) {
                        f = (float)((double)f * 2.0);
                    }
                    this.capPercent -= f;
                } else {
                    if ((this.outpost.getControllingFaction() == null || !this.outpost.getControllingFaction().equals(this.attackingFaction.getTag())) && this.doesFactionControlAnOutpost(this.attackingFaction.getTag())) {
                        this.notifyFactionOfOutpostAlreadyClaimed(this.attackingFaction);
                        return;
                    }
                    float capPercentIncreaseInterval2 = this.getCapPercentIncreaseInterval(FMC);
                    this.getClass();
                    float f = capPercentIncreaseInterval2 * 1.0f;
                    if (this.factionUpgrades) {
                        f = this.applyFactionUpgrades(this.attackingFaction, f);
                    }
                    if (strongestFactionHasMask) {
                        f = (float)((double)f * 2.0);
                    }
                    this.capPercent += f;
                    this.defendingFaction = this.attackingFaction;
                }
            } else if (this.isAFactionDefending() && this.defendingFaction.getTag().equals(strongestFaction)) {
                if (this.capPercent < 1.0f) {
                    float capPercentIncreaseInterval3 = this.getCapPercentIncreaseInterval(FMC);
                    this.getClass();
                    float f = capPercentIncreaseInterval3 * 1.0f;
                    if (this.factionUpgrades) {
                        f = this.applyFactionUpgrades(this.defendingFaction, f);
                    }
                    if (strongestFactionHasMask) {
                        f = (float)((double)f * 2.0);
                    }
                    this.capPercent += f;
                }
                if (!FMC.containsKey(this.attackingFaction.getTag())) {
                    this.attackingFaction = null;
                }
            } else {
                this.attackingFaction = Factions.i.getByTag(strongestFaction);
                if (this.isAFactionDefending()) {
                    float capPercentDecreaseInterval2 = this.getCapPercentDecreaseInterval(FMC);
                    this.getClass();
                    float f = capPercentDecreaseInterval2 * 1.0f;
                    if (this.factionUpgrades) {
                        f = this.applyFactionUpgrades(this.attackingFaction, f);
                    }
                    if (strongestFactionHasMask) {
                        f = (float)((double)f * 2.0);
                    }
                    this.capPercent -= f;
                } else {
                    Bukkit.getLogger().info("[CosmicOutposts] Irregular call, no faction defending but new attacking faction?");
                    float capPercent = this.capPercent;
                    float capPercentIncreaseInterval4 = this.getCapPercentIncreaseInterval(FMC);
                    this.getClass();
                    this.capPercent = capPercent + capPercentIncreaseInterval4 * 1.0f;
                }
            }
            if (this.outpost.getControllingFaction() == null) {
                this.doOutpostNotOwnedLogic(playersInCapzone);
            } else {
                this.doOutpostOwnedLogic(playersInCapzone);
            }
            this.capPercent = this.roundCapPercent();
        }
    }

    private boolean isAFactionDefending() {
        return this.defendingFaction != null;
    }

    private boolean isAFactionAttacking() {
        return this.attackingFaction != null;
    }

    private void doOutpostNotOwnedLogic(List<Player> playersInCapzone) {
        if (this.capPercent <= 0.0f) {
            if (this.defendingFaction != null) {
                this.defendingFaction = null;
            } else {
                this.outpost.broadcastOutpostMessage("&c&l(!) &c&n" + this.attackingFaction.getTag() + "&c is no longer assaulting the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!");
                this.attackingFaction = null;
            }
            return;
        }
        if (this.iterations % 15 == 0) {
            if (this.defendingFaction != null && this.attackingFaction != null && !this.defendingFaction.getTag().equals(this.attackingFaction.getTag())) {
                if (this.outpost.getCapStatus().contains("BLOCKED")) {
                    this.outpost.broadcastOutpostMessage("&7&l(!) &7&n" + this.defendingFaction.getTag() + "&7's cap of &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost&7 is blocked!&7 [&7" + this.roundCapPercentDisplay() + "%&7]");
                } else {
                    this.outpost.broadcastOutpostMessage("&c&l(!) &c&n" + this.defendingFaction.getTag() + "&c is losing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&c" + this.roundCapPercentDisplay() + "%&7]");
                }
            } else if (this.outpost.getCapStatus().contains("BLOCKED")) {
                this.outpost.broadcastOutpostMessage("&7&l(!) &7&n" + this.attackingFaction.getTag() + "&7's cap of &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost&7 is blocked!&7 [&7" + this.roundCapPercentDisplay() + "%&7]");
            } else if (this.attackingFaction != null) {
                this.outpost.broadcastOutpostMessage("&e&l(!) &e&n" + this.attackingFaction.getTag() + "&e is capturing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&e" + this.roundCapPercentDisplay() + "&7%]");
            }
        }
        if (this.capPercent < 1.0f) {
            return;
        }
        if (this.doesFactionControlAnOutpost(this.attackingFaction.getTag())) {
            this.notifyFactionOfOutpostAlreadyClaimed(this.attackingFaction);
            this.attackingFaction = null;
            return;
        }
        this.outpost.setControllingFaction(this.attackingFaction.getTag());
        this.outpost.setCapTime(System.currentTimeMillis());
        String colorCode = "&a";
        if (this.outpost.getOutpostTier() == OutpostTier.HERO) {
            colorCode = "&b";
        }
        if (this.outpost.getOutpostTier() == OutpostTier.COSMONAUT) {
            colorCode = "&6";
        }
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)("" + colorCode + "&l(!) " + colorCode + "&n&l" + this.attackingFaction.getTag() + "" + colorCode + "&l have claimed the &n&l" + this.outpost.getOutpostTier().chatPrefix + " Outpost!")));
        Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)("&7Use " + colorCode + "&l/outpost &7to view more information.")));
        Bukkit.broadcastMessage((String)"");
        this.defendingFaction = this.attackingFaction;
        this.attackingFaction = null;
        Bukkit.getServer().getScheduler().runTask((Plugin)CosmicOutpostsPlugin.get_(), new Runnable(){

            @Override
            public void run() {
                for (Player p : CapTask.this.outpost.getWorld().getPlayers()) {
                    if (CosmicOutpostsAPI.getOutpostPlayerControls(p) != null && CosmicOutpostsAPI.getOutpostPlayerControls(p).equals(CapTask.this.outpost)) {
                        p.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicOutpostsPlugin.get_(), (Object)true));
                        p.teleport(CapTask.this.outpost.getDefenderSpawn(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                        p.removeMetadata("ignore_combat_tag", (Plugin)CosmicOutpostsPlugin.get_());
                        continue;
                    }
                    p.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicOutpostsPlugin.get_(), (Object)true));
                    p.teleport(CapTask.this.outpost.getAttackerSpawn(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    p.removeMetadata("ignore_combat_tag", (Plugin)CosmicOutpostsPlugin.get_());
                }
            }
        });
        for (Player pla : playersInCapzone) {
            Faction fac = FactionUtils.getFaction((Player)pla, (boolean)true);
            if (fac == null || !fac.equals((Object)this.defendingFaction)) continue;
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(pla, "OUTPOST_CAP", new Object[]{this.outpost.getOutpostTier().name()}));
        }
    }

    private void doOutpostOwnedLogic(List<Player> playersInCapzone) {
        if (this.capPercent <= 0.0f) {
            Bukkit.broadcastMessage((String)"");
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)("&c&l(!) &c&n&l" + this.defendingFaction.getTag() + "&c&l have lost the &n&l" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&c&l They had control for &n" + this.outpost.getTimeStringSinceLastCap() + "!")));
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)"&7Use &c&l/outpost &7to view more information."));
            Bukkit.broadcastMessage((String)"");
            long startCapTime = this.outpost.getCapTime();
            int secondsCappedFor = (int)((System.currentTimeMillis() - startCapTime) / 1000L);
            CosmicOutpostsAPI.incrementCapTime(Factions.i.getByTag(this.defendingFaction.getTag()), this.outpost.getOutpostTier(), secondsCappedFor);
            Bukkit.getLogger().info("Adding " + secondsCappedFor + " of cap time to " + this.outpost.getControllingFaction());
            if (playersInCapzone != null) {
                for (Player player : playersInCapzone) {
                    Faction plFaction = FactionUtils.getFaction((Player)player);
                    if (plFaction == null || !plFaction.equals((Object)this.attackingFaction)) continue;
                    Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(player, "OUTPOST_UNCAP", new Object[]{this.outpost.getOutpostTier().name()}));
                }
            }
            if (this.doesFactionControlAnOutpost(this.attackingFaction.getTag())) {
                this.notifyFactionOfOutpostAlreadyClaimed(this.attackingFaction);
                this.attackingFaction = null;
                this.defendingFaction = null;
            } else {
                this.outpost.broadcastOutpostMessage("&e&l(!) &e&n" + this.attackingFaction.getTag() + "&e is capturing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&e0.0%&7]");
                this.defendingFaction = this.attackingFaction;
            }
            this.outpost.setCapTime(System.currentTimeMillis());
            this.outpost.setControllingFaction(null);
            return;
        }
        if (this.iterations % 15 == 0) {
            if (this.outpost.getCapStatus().contains("SECURING")) {
                this.outpost.broadcastOutpostMessage("&a&l(!) &a&n" + this.defendingFaction.getTag() + "&a is securing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&a" + this.capPercent * 100.0f + "%&7]");
            } else if (!this.outpost.getCapStatus().contains("CONTROLLED")) {
                if (this.outpost.getCapStatus().contains("CONTESTED")) {
                    this.outpost.broadcastOutpostMessage("&e&l(!) &e&n" + this.defendingFaction.getTag() + "&e's &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost&e is being contested!&7 [&e" + this.capPercent * 100.0f + "%&7]");
                } else {
                    this.outpost.broadcastOutpostMessage("&c&l(!) &c&n" + this.defendingFaction.getTag() + "&c is losing the &n" + this.outpost.getOutpostTier().chatPrefix + " Outpost!&7 [&c" + this.capPercent * 100.0f + "%&7]");
                }
            }
        }
    }

    private float getCapPercentIncreaseInterval(FactionMemberCounts factionCounts) {
        if (factionCounts.size() > 1) {
            return 0.0f;
        }
        int factionMembers = (Integer)factionCounts.values().iterator().next();
        return Math.min(Math.min(0.005f, 0.001f * (float)factionMembers), 1.0f - this.capPercent);
    }

    private float getCapPercentDecreaseInterval(FactionMemberCounts factionCounts) {
        if (this.outpost.getControllingFaction() != null) {
            if (factionCounts.size() > 1 || !((String)factionCounts.keySet().iterator().next()).equals(this.outpost.getControllingFaction())) {
                int hostiles = 0;
                Iterator iterator = factionCounts.values().iterator();
                while (iterator.hasNext()) {
                    int members = (Integer)iterator.next();
                    hostiles += members;
                }
                if (factionCounts.containsKey(this.outpost.getControllingFaction())) {
                    return 0.0f;
                }
                int hostilesMod = Math.max(1, Math.min(5, hostiles / 3));
                return Math.min(0.005f, 0.001f * (float)hostilesMod);
            }
        } else {
            if (factionCounts.size() > 1) {
                return 0.0f;
            }
            String faction = (String)factionCounts.keySet().iterator().next();
            if (faction.equals(this.getAttackingFaction().getTag()) && !faction.equals(this.getDefendingFaction().getTag())) {
                int hostiles2 = 0;
                Iterator members = factionCounts.values().iterator();
                while (members.hasNext()) {
                    int members2 = (Integer)members.next();
                    hostiles2 += members2;
                }
                if (factionCounts.containsKey(this.outpost.getControllingFaction())) {
                    hostiles2 -= ((Integer)factionCounts.get(this.outpost.getControllingFaction())).intValue();
                }
                int hostilesMod2 = Math.max(1, Math.min(5, hostiles2 / 3));
                return Math.min(0.005f, 0.001f * (float)hostilesMod2);
            }
        }
        return 0.0f;
    }

    public void notifyFactionOfOutpostAlreadyClaimed(Faction f) {
        if (!factionNotifyTimes.containsKey(f.getTag()) || System.currentTimeMillis() - factionNotifyTimes.get(f.getTag()) > 10000L) {
            this.attackingFaction.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) You cannot control more than ONE Astro Outpost at a time!");
            this.attackingFaction.sendMessage(ChatColor.GRAY.toString() + "You will not be allowed to capture the " + this.outpost.getOutpostTier().chatPrefix + " Outpost until you lose control of your other outposts.");
            factionNotifyTimes.put(f.getTag(), System.currentTimeMillis());
        }
    }

    public float roundCapPercent() {
        BigDecimal bd = new BigDecimal(Float.toString(this.capPercent));
        bd = bd.setScale(3, 4);
        return Math.max(0.0f, Math.min(1.0f, bd.floatValue()));
    }

    public float roundCapPercentDisplay() {
        BigDecimal bd = new BigDecimal(Float.toString(this.capPercent * 100.0f));
        bd = bd.setScale(3, 4);
        return Math.max(0.0f, Math.min(100.0f, bd.floatValue()));
    }

    public boolean doesFactionControlAnOutpost(String factionTag) {
        for (Outpost outpost : CosmicOutpostsPlugin.get_().getOutposts()) {
            if (outpost.getControllingFaction() == null || !outpost.getControllingFaction().equals(factionTag)) continue;
            return true;
        }
        return false;
    }

    private String getStrongestFaction(FactionMemberCounts factionCounts) {
        int mostMembers = 0;
        String strongestFaction = null;
        for (Map.Entry data : factionCounts.entrySet()) {
            if ((Integer)data.getValue() <= mostMembers) continue;
            mostMembers = (Integer)data.getValue();
            strongestFaction = (String)data.getKey();
        }
        return strongestFaction;
    }

    public boolean doesPlayerInStrongestFactionHaveOutpostMask(String factionTag, List<Player> playersInCapzone) {
        for (Player p : playersInCapzone) {
            FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
            if (!fp.getFaction().getTag().equals(factionTag) || !MaskUtils.hasMaskEquipped((Player)p, (String)"OUTPOST")) continue;
            return true;
        }
        return false;
    }

    public FactionMemberCounts getFactionMemberCounts(List<Player> l) {
        FactionMemberCounts factionCounts = new FactionMemberCounts();
        for (Player p : l) {
            FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
            if (!fp.getFaction().isNormal()) continue;
            factionCounts.put(fp.getFaction().getTag(), factionCounts.containsKey(fp.getFaction().getTag()) ? (Integer)factionCounts.get(fp.getFaction().getTag()) + 1 : 1);
        }
        return factionCounts;
    }

    public List<Player> getPlayersInCapZone() {
        ArrayList<Player> l = new ArrayList<Player>();
        for (Player p : Arkkit.get().getAsyncPlayerMap().values()) {
            if (!p.getWorld().equals((Object)this.outpost.getWorld()) || !WorldGuardUtils.getRegionName(p.getLocation()).equals(Outpost.OUTPOST_CAP_REGION) || p.getGameMode() != GameMode.SURVIVAL || CosmicOutpostsPlugin.get_().getEss3().getUser(p).isVanished()) continue;
            l.add(p);
        }
        return l;
    }

    public float applyFactionUpgrades(Faction faction, float f) {
        if (this.factionUpgrades) {
            int perkLevel = FactionUpgradeAPI.getPerkLevel((Faction)faction, (FactionUpgrade)FactionUpgrade.OUTPOST_CONTROL);
            if (perkLevel == 1) {
                f *= 1.1f;
            }
            if (perkLevel == 2) {
                f = (float)((double)f * 1.25);
            }
            if (perkLevel == 3) {
                f = (float)((double)f * 1.5);
            }
            if (perkLevel == 4) {
                f *= 2.0f;
            }
            if (perkLevel == 5) {
                f = (float)((double)f * 2.25);
            }
        }
        return f;
    }

    public float getCapPercent() {
        return this.capPercent;
    }

    public void setCapPercent(float capPercent) {
        this.capPercent = capPercent;
    }

    public Faction getAttackingFaction() {
        return this.attackingFaction;
    }

    public void setAttackingFaction(Faction attackingFaction) {
        this.attackingFaction = attackingFaction;
    }

    public Faction getDefendingFaction() {
        return this.defendingFaction;
    }

    public void setDefendingFaction(Faction defendingFaction) {
        this.defendingFaction = defendingFaction;
    }

    public Outpost getOutpost() {
        return this.outpost;
    }
}

