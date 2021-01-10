/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.utils.TitleManager
 *  com.cosmicpvp.cosmicutils.utils.TitleManager$TitleColor
 *  com.cosmicpvp.cosmicutils.utils.VanishUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.gmail.nossr50.api.PartyAPI
 *  com.gmail.nossr50.runnables.skills.BleedTimerTask
 *  com.gmail.nossr50.util.StringUtils
 *  com.google.common.collect.Lists
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.factionupgrades.utils.ItemBuilder
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.arkhamnetwork.Arkkit.patches.CombatLog
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.listeners.PlayerPotionEffectListener
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Instrument
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Note
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicduel.struct.newduel;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.VanishUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.gmail.nossr50.api.PartyAPI;
import com.gmail.nossr50.runnables.skills.BleedTimerTask;
import com.gmail.nossr50.util.StringUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.listeners.DuelListener;
import me.ifamasssxd.cosmicduel.listeners.SpectateInventory;
import me.ifamasssxd.cosmicduel.struct.CustomGKit;
import me.ifamasssxd.cosmicduel.struct.CustomKit;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.Kit;
import me.ifamasssxd.cosmicduel.struct.enums.*;
import me.ifamasssxd.cosmicduel.utils.*;
import me.ifamasssxd.factionupgrades.utils.ItemBuilder;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.listeners.PlayerPotionEffectListener;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassicDuel
extends NewDuel {
    private static int id = 1;
    private boolean ending = false;
    private final DecimalFormat format = new DecimalFormat("#.#");

    public ClassicDuel(Player initiator, List<String> pendingInvites) {
        super(initiator, pendingInvites);
        this.duelID = id++;
    }

    public ClassicDuel(Player initiator, List<String> pendingInvites, boolean ranked) {
        super(initiator, pendingInvites, ranked);
        this.duelID = id++;
    }

    @Override
    public String getName() {
        return "1v1";
    }

    @Override
    public DuelType getDuelType() {
        return DuelType.ONE_VS_ONE;
    }

    @Override
    public int getPlayersToStart() {
        return 2;
    }

    @Override
    public void addPlayer(Player player, boolean checkStart) {
        this.players.add(player);
        player.setMetadata("in_duel", new FixedMetadataValue(CosmicDuel.get(), ""));
        if (checkStart && this.players.size() >= this.getPlayersToStart()) {
            this.setCreateState(DuelCreateState.STAKES);
            this.stakeInventory = new NewStakeInventory(this, this.getPlayers().get(0), player);
            for (Player p : this.getPlayers()) {
                p.setMetadata("close_exempt", new FixedMetadataValue(CosmicDuel.get(), ""));
                p.closeInventory();
                p.openInventory(this.stakeInventory.getInventory());
            }
        }
    }

    private void setupPlayer(Player player) {
        CosmicDuel.logMessage("Preparing " + player.getName() + " for duel " + this.duelID);
        this.storedPlayers.put(player.getUniqueId(), new StoredPlayer(player));
        this.originalLocation.put(player.getUniqueId(), player.getLocation());
        player.setMetadata("in_duel", new FixedMetadataValue(CosmicDuel.get(), ""));
        Location spawn = this.checkIfSpawnLoaded(false);
        this.forceTeleport(player, spawn);
        System.out.println("[CosmicDuels] Teleporting " + player.getName() + " to " + spawn);
        this.clearChat(player);
        this.resetPlayerStats(player);
        this.applySettings(player);
        this.checkMcmmoParty(player);
    }

    @Override
    public void onStart() {
        this.setDuelState(DuelState.STARTING);
        SpectateInventory.updateInventory();
        for (Player p : this.getPlayers()) {
            this.setupPlayer(p);
        }
        this.clearArena();
        int i = 0;
        while (true) {
            if (i >= this.players.size()) {
                final ClassicDuel duel = this;
                new BukkitRunnable(){
                    final AtomicInteger count = new AtomicInteger(10);

                    public void run() {
                        if (ClassicDuel.this.getPlayers().size() < ClassicDuel.this.getPlayersToStart()) {
                            this.cancel();
                            DuelAPI.cancelDuel(duel, CancelReason.NOT_ENOUGH_PLAYERS);
                            return;
                        }
                        if (this.count.decrementAndGet() <= 0) {
                            this.cancel();
                            CosmicDuel.logMessage("Starting duel in arena " + ClassicDuel.this.arena.getName() + " with id " + ClassicDuel.this.duelID + " settings -> " + ClassicDuel.this.getSettings().toString());
                            ClassicDuel.this.start();
                            return;
                        }
                        for (Player p : ClassicDuel.this.getPlayers()) {
                            p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "The duel will start in " + ChatColor.YELLOW + ChatColor.BOLD.toString() + this.count.get() + "s.");
                            p.playNote(p.getLocation(), Instrument.PIANO, new Note(3));
                        }
                    }
                }.runTaskTimer(CosmicDuel.get(), 20L, 20L);
                return;
            }
            if (this.getDuelType() == DuelType.ONE_VS_ONE) {
                Player p;
                p = this.players.get(i);
                Player pl = this.getPlayers().get(i == this.getPlayers().size() - 1 ? 0 : i + 1);
                p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "Your " + (this.isRanked() ? ChatColor.YELLOW + ChatColor.BOLD.toString() + "RANKED " : "") + ChatColor.YELLOW + "duel with " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + pl.getName() + ChatColor.YELLOW + " will begin shortly...");
            }
            ++i;
        }
    }

    @Override
    public void onDuelEnd(EndReason endReason, Player loser) {
        this.ending = true;
        this.resetEnvoys();
        CosmicDuel.logMessage("Ending duel for reason: " + endReason.name() + " loser: " + (loser != null ? loser.getName() : "none"));
        if (endReason == EndReason.WIN) {
            if (this.players.size() >= 1) {
                Player winner = this.players.get(0);
                String winningMsg = this.getColoredMessage("&e&l(!) &a&n%s&e has defeated %s!", winner.getName(), loser.getName());
                JSONMessage jsonMessage = new JSONMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Inventories: ");
                this.sendMessage("", true);
                this.sendMessage(winningMsg, true);
                if (!this.getEndingInventoryCache().containsKey(winner.getName())) {
                    UUID uuid = CosmicDuel.get().getDuelHistoryManager().storeInventorySnapshot(winner);
                    this.getEndingInventoryCache().put(winner.getName(), uuid);
                }
                int index = 0;
                for (Map.Entry<String, UUID> invs : this.getEndingInventoryCache().entrySet()) {
                    boolean includeComma = true;
                    if (index++ == this.getEndingInventoryCache().size() - 1) {
                        includeComma = false;
                    }
                    jsonMessage.addRunCommand(invs.getKey() + (includeComma ? ", " : ""), winner.getName().equals(invs.getKey()) ? ChatColor.GREEN : ChatColor.RED, "/viewdeathinventory " + this.getEndingInventoryCache().get(invs.getKey()).toString(), ChatColor.GRAY + "Click to view duel inventory.");
                }
                this.sendJsonMessage(jsonMessage, true);
                this.sendMessage("", true);
                if (loser != null) {
                    loser.sendMessage("");
                    loser.sendMessage(winningMsg);
                    jsonMessage.sendToPlayer(loser);
                    loser.sendMessage("");
                }
                this.removePlayer(winner, Reason.WIN, false);
                winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
                RandomFireworks.getManager().launchRandomFirework(winner.getLocation());
                if (this.settings.hasSetting(Setting.BOUNTIES) && winner != null) {
                    CosmicDuel.logMessage("Giving bounty to " + winner.getName());
                    this.awardBounty(winner, loser);
                }
                this.awardStakes(winner);
                if (this.settings.hasSetting(Setting.DEATH_CERT)) {
                    this.giveWinnerPage(winner, loser);
                }
                if (this.settings.hasSetting(Setting.DROPITEMS) && this.kit.equals(Kit.BYOB)) {
                    CosmicDuel.logMessage("Dropping all of " + loser.getName() + "'s Items to " + winner.getName());
                    Inventory inv = this.getDroppedItemsInventory(loser);
                    winner.openInventory(inv);
                }
                if (this.isRanked()) {
                    winner.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You defeated " + loser.getName() + " in a Ranked Duel!");
                    loser.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You have been defeated by " + winner.getName() + " in a Ranked Duel!");
                }
                DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(winner);
                DuelPlayer loserPlayer = DuelPlayer.getDuelPlayer(loser);
                if (duelPlayer != null && loserPlayer != null) {
                    if (this.isRanked()) {
                        double biggest = Math.max(duelPlayer.getElo(), loserPlayer.getElo());
                        double lowest = Math.min(duelPlayer.getElo(), loserPlayer.getElo());
                        double extraWin = (1.0 - lowest / biggest) * 2.0 * 10.0;
                        double toGain = Math.max(1.0, duelPlayer.getElo() > loserPlayer.getElo() ? 10.0 - extraWin : 10.0 + extraWin) * 5.0;
                        double extraLoss = (1.0 - lowest / biggest) * 2.0 * 8.0;
                        double toLose = Math.max(1.0, loserPlayer.getElo() < duelPlayer.getElo() ? 8.0 - extraLoss : 8.0 + extraLoss) * 5.0;
                        double beforeElo = duelPlayer.getElo();
                        duelPlayer.setRankedWins(duelPlayer.getRankedWins() + 1);
                        duelPlayer.setElo(duelPlayer.getElo() + toGain);
                        winner.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + Math.round(toGain) + " ELO " + ChatColor.GRAY + "(" + (int)beforeElo + " -> " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + (int)duelPlayer.getElo() + ChatColor.GRAY + " ELO)");
                        double loserBeforeElo = loserPlayer.getElo();
                        loserPlayer.setRankedLosses(loserPlayer.getRankedLosses() + 1);
                        loserPlayer.setElo(loserPlayer.getElo() - toLose);
                        loser.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "- " + Math.round(toLose) + " ELO " + ChatColor.GRAY + "(" + (int)loserBeforeElo + " -> " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + (int)loserPlayer.getElo() + ChatColor.GRAY + " ELO)");
                        loserPlayer.setLastRankedMatch(System.currentTimeMillis());
                        duelPlayer.setLastRankedMatch(System.currentTimeMillis());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contest add " + winner.getName() + " rankedduels 1");
                    } else {
                        duelPlayer.setWins(duelPlayer.getWins() + 1);
                        loserPlayer.setLosses(loserPlayer.getLosses() + 1);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contest add " + winner.getName() + " unrankedduels 1");
                    }
                    loserPlayer.saveRankedStats(true);
                    duelPlayer.saveRankedStats(true);
                    Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(winner, "DUEL_WIN", new Object[]{this.isRanked(), loser}));
                    Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(loser, "DUEL_LOSE", new Object[]{this.isRanked(), winner}));
                }
                winner.sendMessage("");
                loser.sendMessage("");
                //this.sendTitle(ChatColor.GREEN + ChatColor.BOLD.toString() + "Winner", TitleManager.TitleColor.GREEN, winner, loser);
                //this.sendSubTitle(ChatColor.GRAY + ChatColor.BOLD.toString() + winner.getName() + (this.isRanked() && duelPlayer != null ? ChatColor.GRAY + " (" + (int)duelPlayer.getElo() + " ELO)" : ""), TitleManager.TitleColor.AQUA, winner, loser);
            }
        } else if (endReason == EndReason.RELOAD || endReason == EndReason.NO_WINNER) {
            this.onEnd(false);
            this.returnStakes();
            DuelAPI.cancelDuel(this, CancelReason.RELOAD);
            return;
        }
        this.duelState = DuelState.ENDING;
        new BukkitRunnable(){
            int count = 5;

            public void run() {
                int count;
                this.count = count = this.count - 1;
                if (count <= 0) {
                    this.cancel();
                    ClassicDuel.this.onEnd(true);
                    return;
                }
                ClassicDuel.this.sendMessage(ClassicDuel.this.getColoredMessage("&c&l(!) &cThe duel will end in %s.", this.count), true);
            }
        }.runTaskTimer(CosmicDuel.get(), 20L, 20L);
    }

    @Override
    public Inventory getDroppedItemsInventory(Player loser) {
        Inventory inv = Bukkit.createInventory(null, loser.getInventory().getSize() + 9, loser.getName() + "'s Death Loot");
        int index = 0;
        for (ItemStack armor : loser.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                inv.setItem(index, armor);
            }
            ++index;
        }
        loser.getInventory().setArmorContents(null);
        this.removePotionEffects(loser);
        index = 9;
        for (ItemStack is : loser.getInventory()) {
            if (is != null && is.getType() != Material.AIR) {
                inv.setItem(index, is);
            }
            ++index;
        }
        loser.getInventory().clear();
        loser.updateInventory();
        return inv;
    }

    @Override
    public void giveWinnerPage(Player winner, Player loser) {
        if (winner == null || loser == null) {
            Bukkit.getLogger().info("Null player for giveWinnerPage() FeelsBadMan");
            return;
        }
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
            String extraKitName = null;
            if (this.getKit() != Kit.BYOB) {
                extraKitName = this.getCustomGKit() != null && this.getKit() == Kit.GKIT ? ChatColor.GRAY + "using a " + ChatColor.WHITE + StringUtils.getCapitalized(this.getCustomGKit().getKitName()) + ChatColor.GRAY + " /gkit." : ChatColor.GRAY + "using a " + ChatColor.WHITE + this.getKit().getName(true) + ChatColor.GRAY + " kit.";
            }
            ItemStack is = ItemBuilder.buildItem(Material.PAPER, ChatColor.WHITE + ChatColor.BOLD.toString() + loser.getName() + "'s Death Certificate", new String[]{ChatColor.WHITE + loser.getName() + ChatColor.GRAY + " was defeated in a " + ChatColor.WHITE + this.getName(), ChatColor.GRAY + "duel on " + ChatColor.WHITE + format.format(date) + " at " + timeFormat.format(date), ChatColor.GRAY + "in the " + ChatColor.WHITE + this.getArena().getName() + " Arena " + ChatColor.GRAY + "by " + ChatColor.WHITE + winner.getName(), extraKitName});
            if (winner.getInventory().firstEmpty() == -1) {
                CollectionBin.addItem(winner, is);
            } else {
                winner.getInventory().addItem(is);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void awardBounty(Player winner, Player loser) {
    }

    private void clearArena() {
        for (Entity e : this.getArena().getSpawnLocation(true).getWorld().getEntities()) {
            if (e instanceof Player || !WorldGuardUtils.getRegionName(e.getLocation()).toLowerCase().startsWith(this.getArena().getRegion().toLowerCase())) continue;
            e.remove();
        }
    }

    private void checkMcmmoParty(Player p) {
        if (!CosmicDuel.mcMMOEnabled) {
            return;
        }
        if (this.settings.hasSetting(Setting.MCMMO) && PartyAPI.inParty(p)) {
            String name = PartyAPI.getPartyName(p);
            PartyAPI.removeFromParty(p);
            p.setMetadata("mcmmo_party", new FixedMetadataValue(CosmicDuel.get(), name));
        }
    }

    private void clearChat(Player p) {
        for (int i = 0; i < 35; ++i) {
            p.sendMessage("");
        }
    }

    private Location checkIfSpawnLoaded(boolean random) {
        Location retr = this.getArena().getSpawnLocation(random);
        if (retr.getChunk() != null && !retr.getChunk().isLoaded()) {
            retr.getChunk().load();
        }
        return retr;
    }

    @Override
    public void start() {
        this.duelState = DuelState.PLAYING;
        for (Player p : this.getPlayers()) {
            p.setHealth(p.getMaxHealth());
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 0.6f, 1.0f);
            p.sendMessage(this.getColoredMessage("&e&lFIGHT!"));
            if (this.settings.hasSetting(Setting.ENVOY)) {
                p.sendMessage(this.getColoredMessage("&6&l(!) &6Envoys can be seen falling from the sky!"));
            }
            RandomFireworks.getManager().launchRandomFirework(p.getLocation());
        }
    }

    @Override
    public void onEnd(boolean cancelDuel) {
        this.duelState = DuelState.ENDED;
        for (Player pl : Lists.newArrayList(this.getPlayers())) {
            this.removePlayer(pl, Reason.WIN, false);
        }
        this.clearSpectators();
        if (cancelDuel) {
            DuelAPI.cancelDuel(this, CancelReason.DUEL_END);
        }
    }

    @Override
    public void removePlayer(Player player, Reason reason, boolean createSpectator) {
        if (!this.players.contains(player)) {
            return;
        }
        CosmicDuel.logMessage("Removing " + player.getName() + " from duel ID: " + this.duelID);
        this.players.remove(player);
        player.setMetadata("lastDuelEnd", new FixedMetadataValue(CosmicDuel.get(), MinecraftServerUtils.getCurrentServerTick()));
        player.removeMetadata("in_duel", CosmicDuel.get());
        if (reason != Reason.WIN) {
            this.sendMessage(reason.getReason(player), true);
        }
        if (!this.getEndingInventoryCache().containsKey(player.getName())) {
            UUID uuid = CosmicDuel.get().getDuelHistoryManager().storeInventorySnapshot(player);
            this.getEndingInventoryCache().put(player.getName(), uuid);
        }
        this.resetPlayer(player);
        this.removePotionEffects(player);
        if (!this.ending && createSpectator && !this.willDuelEnd()) {
            this.prepareSpectator(player);
        } else {
            this.resetMcMMO(player);
            this.returnPlayerToStartLocation(player);
        }
        this.returnItems(player, reason == Reason.RELOAD);
        if (reason == Reason.WIN || reason == Reason.RELOAD || reason == Reason.NO_WINNER) {
            return;
        }
        if (this.getCreateState() != DuelCreateState.DONE) {
            return;
        }
        if (this.willDuelEnd() && this.isCurrentlyInDuel()) {
            this.onDuelEnd(EndReason.WIN, player);
        }
    }

    public boolean willDuelEnd() {
        return this.players.size() <= 1 && this.getDuelState().isValid();
    }

    @Override
    public void resetPlayerStats(Player p) {
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.setFallDistance(0.0f);
        p.setHealth(p.getMaxHealth());
        p.spigot().setCollidesWithEntities(true);
        if (CosmicDuel.mcMMOEnabled) {
            BleedTimerTask.remove(p);
        }
        if (!VanishUtils.isVanished(p)) {
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                pl.showPlayer(p);
            }
        }
    }

    @Override
    public void returnItems(Player p, boolean reloading) {
        if (!this.storedPlayers.containsKey(p.getUniqueId())) {
            return;
        }
        StoredPlayer player = this.storedPlayers.get(p.getUniqueId());
        player.restorePlayer(p, reloading);
        this.storedPlayers.remove(p.getUniqueId());
    }

    @Override
    public void returnPlayerToStartLocation(Player p) {
        if (this.originalLocation.containsKey(p.getUniqueId())) {
            this.forceTeleport(p, this.originalLocation.get(p.getUniqueId()));
        }
        this.originalLocation.remove(p.getUniqueId());
        if (!DuelAPI.isRebooting() && MinecraftServerUtils.isRunning()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicDuel.get(), () -> {
                CombatLog.unflagCombat(p);
                if (p.hasMetadata("cl_combat")) {
                    p.removeMetadata("cl_combat", Arkkit.get());
                }
            }, 5L);
        } else {
            CombatLog.unflagCombat(p);
        }
    }

    @Override
    public void resetMcMMO(Player p) {
        if (!CosmicDuel.mcMMOEnabled) {
            return;
        }
        if (!this.settings.hasSetting(Setting.MCMMO)) {
            p.removeMetadata("no_mcmmo_skills", CosmicDuel.get());
        }
        if (p.hasMetadata("mcmmo_party") && p.getMetadata("mcmmo_party").size() > 0) {
            String name = p.getMetadata("mcmmo_party").get(0).asString();
            if (PartyAPI.inParty(p)) {
                return;
            }
            PartyAPI.addToParty(p, name);
        }
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public List<Player> getSpectators() {
        return this.spectators;
    }

    @Override
    public void prepareSpectator(Player spectator) {
        if (this.getDuelState() == DuelState.ENDING || this.getDuelState() == DuelState.IN_QUEUE) {
            return;
        }
        if (this.spectators.contains(spectator)) {
            return;
        }
        spectator.setMetadata("spectator", new FixedMetadataValue(CosmicDuel.get(), ""));
        this.spectators.add(spectator);
        this.resetPlayerStats(spectator);
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.hidePlayer(spectator);
        }
        spectator.spigot().setCollidesWithEntities(false);
        if (!this.originalLocation.containsKey(spectator.getUniqueId())) {
            this.originalLocation.put(spectator.getUniqueId(), spectator.getLocation());
        }
        //TitleManager.sendTitle(spectator, ChatColor.GREEN + ChatColor.BOLD.toString() + "SPECTATOR", TitleManager.TitleColor.GREEN);
        //TitleManager.sendSubTitle(spectator, ChatColor.YELLOW + "You are now spectating the duel.", TitleManager.TitleColor.GREEN);
        spectator.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You are now " + ChatColor.GREEN + ChatColor.UNDERLINE + "SPECTATING" + ChatColor.GREEN + " this duel.");
        spectator.sendMessage(ChatColor.GRAY + "You can use " + ChatColor.YELLOW + "'/duel leave'" + ChatColor.GRAY + " to stop spectating.");
        this.forceTeleport(spectator, this.getArena().getSpawnLocation(true));
        for (Player spec : this.getSpectators()) {
            spectator.hidePlayer(spec);
        }
        spectator.setAllowFlight(true);
        spectator.setFlying(true);
    }

    @Override
    public void removeSpectator(Player p) {
        p.removeMetadata("spectator", CosmicDuel.get());
        if (!this.spectators.contains(p)) {
            return;
        }
        this.spectators.remove(p);
        this.resetPlayer(p);
        this.resetMcMMO(p);
        this.returnPlayerToStartLocation(p);
        if (p.hasPotionEffect(PotionEffectType.POISON)) {
            p.removePotionEffect(PotionEffectType.POISON);
        }
    }

    @Override
    public void clearSpectators() {
        for (Player pl : this.getSpectators()) {
            this.removeSpectator(pl);
        }
    }

    @Override
    public void resetPlayer(Player p) {
        this.resetPlayerStats(p);
    }

    @Override
    public boolean isCurrentlyInDuel() {
        return this.getDuelState().isValid() && this.getCreateState() == DuelCreateState.DONE;
    }

    @Override
    public Player getOppositePlayer(Player p) {
        if (this.getPlayers().size() == 2) {
            if (p.equals(this.getPlayers().get(0))) {
                return this.getPlayers().get(1);
            }
            if (p.equals(this.getPlayers().get(1))) {
                return this.getPlayers().get(0);
            }
        }
        return null;
    }

    @Override
    public void applySettings(Player p) {
        if (!this.settings.hasSetting(Setting.MCMMO) && CosmicDuel.mcMMOEnabled) {
            p.setMetadata("no_mcmmo_skills", new FixedMetadataValue(CosmicDuel.get(), ""));
        }
        if (this.kit != Kit.BYOB) {
            this.wipePlayer(p);
            this.applyKit(p);
        } else {
            boolean hadDisabledItems = false;
            for (ItemStack is : p.getInventory()) {
                if (is == null || is.getType() == Material.AIR || !this.isDisabledDuelItem(is) && this.isValidItem(is)) continue;
                p.getInventory().removeItem(is);
                hadDisabledItems = true;
            }
            p.updateInventory();
            if (!this.settings.hasSetting(Setting.ARMOR)) {
                for (ItemStack armor : p.getInventory().getArmorContents()) {
                    if (armor == null || armor.getType() == Material.AIR) continue;
                    CosmicDuel.logMessage("Removing armor for duel " + p.getName());
                    p.sendMessage(ChatColor.RED + "Your armor has been stored and will be returned after the duel ends.");
                    break;
                }
                p.getInventory().setArmorContents(null);
                p.updateInventory();
            }
            if (hadDisabledItems) {
                p.sendMessage(this.getColoredMessage("&c&l(!) &cYou had items that were disabled in this duel."));
                p.sendMessage(ChatColor.GRAY + "They will be returned to you once the duel is over.");
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicDuel.get(), () -> {
            if (p.isValid()) {
                PlayerPotionEffectListener.rescanPlayerArmor(p);
            }
        }, 4L);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void applyKit(Player p) {
        CustomKit defaultKit;
        List<ItemStack> kitItems;
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(p);
        if (this.isRanked()) {
            kitItems = duelPlayer.getCustomGodSet().getKitItems(true, false);
            kitItems.add(new ItemStack(Material.ENDER_PEARL, 16));
            kitItems.add(new ItemStack(Material.ARROW, 64));
            kitItems.add(new ItemStack(Material.GOLDEN_APPLE, 1));
        } else {
            kitItems = this.getKit().getItems();
        }
        if (this.getCustomGKit() != null && this.getKit() == Kit.GKIT) {
            CustomGKit defaultGkit = duelPlayer.getDefaultGKit(this.getCustomGKit());
            if (defaultGkit != null) {
                kitItems = Lists.newArrayList();
                kitItems.addAll(defaultGkit.getGKitItems());
                kitItems.addAll(defaultGkit.getItems());
            } else {
                kitItems = KitUtils.getMaxedEnchantsFromGKit(this.getCustomGKit());
            }
        } else if ((this.getKit() == Kit.DEBUFF || this.getKit() == Kit.HEALER) && (defaultKit = duelPlayer.getDefaultKit(this.getKit())) != null) {
            kitItems = defaultKit.getKitItems();
        }
        boolean hadDisabledItems = false;
        int potionsToAdd = 0;
        for (ItemStack itemStack : kitItems) {
            ItemStack var7_12 = null;
            ItemStack itemStack2;
            if (this.isDisabledDuelItem(itemStack)) {
                ++potionsToAdd;
                hadDisabledItems = true;
                continue;
            }
            if (this.isRanked() && EnchantmentAPI.getEnchantments(itemStack).size() > 0) {
                ItemStack itemStack3 = EnchantmentBook.applyTransmogScroll(itemStack.clone());
            }
            if (((itemStack2 = KitUtils.addDuelWatermark(var7_12.clone())).getType() == Material.POTION || itemStack2.getType() == Material.MUSHROOM_SOUP) && itemStack2.getAmount() > 1) {
                while (itemStack2.getAmount() > 0) {
                    ItemStack toAdd = itemStack2.clone();
                    toAdd.setAmount(1);
                    itemStack2.setAmount(itemStack2.getAmount() - 1);
                    p.getInventory().addItem(toAdd);
                }
                continue;
            }
            p.getInventory().addItem(itemStack2);
        }
        if (this.isRanked()) {
            Integer var7_15 = null;
            ItemStack potion = new ItemStack(Material.POTION, 1, (short) 16421);
            potion = KitUtils.addDuelWatermark(potion);
            boolean bl = false;
            while (++var7_15 < p.getInventory().getSize()) {
                if (p.getInventory().getItem(var7_15) == null || p.getInventory().getItem(var7_15).getType() == Material.AIR) {
                    p.getInventory().setItem(var7_15, potion);
                }
            }
        }
        for (int j = 0; j < potionsToAdd; ++j) {
            p.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
        }
        p.updateInventory();
        if (hadDisabledItems) {
            p.sendMessage(this.getColoredMessage("&e&l(!) &eYour kit contained some items that were &e&ndisabled&e in this duel."));
        }
    }

    public void wipePlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.updateInventory();
        Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicDuel.get(), () -> this.removePotionEffects(p), 7L);
    }

    public void removePotionEffects(Player p) {
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
    }

    public boolean isDisabledDuelItem(ItemStack is) {
        if (DuelUtils.isArmor(is)) {
            return !this.getSettings().hasSetting(Setting.ARMOR);
        }
        if (DuelUtils.isWeapon(is) && is.getType() != Material.BOW) {
            return !this.getSettings().hasSetting(Setting.WEAPONS);
        }
        switch (is.getType()) {
            case GOLDEN_APPLE: {
                return !this.getSettings().hasSetting(Setting.GOLDEN_APPLES);
            }
            case ENDER_PEARL: {
                return !this.getSettings().hasSetting(Setting.ENDER_PEARLS);
            }
            case BOW: 
            case ARROW: {
                return !this.getSettings().hasSetting(Setting.BOWS);
            }
            case POTION: {
                return !this.getSettings().hasSetting(Setting.POTIONS);
            }
            case COOKED_BEEF: 
            case GOLDEN_CARROT: {
                return !this.getSettings().hasSetting(Setting.FOOD_LOSS);
            }
            case EMERALD: {
                return !this.getSettings().hasSetting(Setting.SOUL_GEM);
            }
            case SKULL_ITEM: {
                return !this.getSettings().hasSetting(Setting.PET);
            }
        }
        return false;
    }

    private boolean isValidItem(ItemStack is) {
        return DuelUtils.isArmor(is) || DuelUtils.isWeapon(is) || is.getType() == Material.POTION || is.getType() == Material.GOLDEN_APPLE || is.getType() == Material.MUSHROOM_SOUP || is.getType() == Material.ARROW || is.getType() == Material.ENDER_PEARL || is.getType() == Material.EMERALD || is.getType() == Material.SKULL_ITEM;
    }

    @Override
    public void awardStakes(Player winner) {
        if (winner == null) {
            this.returnStakes();
            return;
        }
        boolean collectionBin = false;
        for (List<ItemStack> stakeItems : this.stakes.values()) {
            for (ItemStack stakeItem : stakeItems) {
                if (winner.getInventory().firstEmpty() == -1) {
                    CollectionBin.addItem(winner, stakeItem);
                    collectionBin = true;
                    continue;
                }
                winner.getInventory().addItem(stakeItem);
            }
        }
        this.stakes.clear();
        winner.updateInventory();
        if (collectionBin) {
            winner.sendMessage(this.getColoredMessage("&e&l(!) &eYour inventory was full and the stakes have been placed in your &a&n/duel collect&e bin."));
        }
    }

    @Override
    public void returnStakes() {
        for (Map.Entry<UUID, List<ItemStack>> item : this.stakes.entrySet()) {
            Player p = Bukkit.getPlayer(item.getKey());
            boolean collectionBinUsed = false;
            for (ItemStack is : item.getValue()) {
                if (p.getInventory().firstEmpty() == -1) {
                    CollectionBin.addItem(p, is);
                    collectionBinUsed = true;
                    continue;
                }
                p.getInventory().addItem(is);
            }
            if (!collectionBinUsed) continue;
            p.sendMessage("&e&l(!) &eYour inventory was full and the stakes have been placed in your &e&n/duel collect&e bin.");
        }
        this.stakes.clear();
    }

    @Override
    public void resetEnvoys() {
        for (Location l : this.envoys) {
            CosmicSpaceChestsPlugin.chest_locations.remove(l);
            if (l.getBlock().getType() != Material.CHEST) continue;
            DuelListener.removeChest(l);
        }
        this.envoys.clear();
    }

    @Override
    public void onConfirmedQueue() {
        this.setCreateState(DuelCreateState.DONE);
        this.onStart();
    }

    @Override
    public void onQueuePop() {
        this.inQueueMenu = true;
        if (this.isRanked()) {
            final GlobalQueuePopInventory inv = new GlobalQueuePopInventory(this).prepareQueue();
            inv.alertOfQueue();
            new BukkitRunnable(){

                public void run() {
                    if (!inv.wasAccepted() && inv.duel != null) {
                        Player player = inv.getWhoDidntAccept();
                        if (player == null) {
                            DuelAPI.cancelDuel(inv.duel, CancelReason.DENY_QUEUE_POP);
                        } else {
                            inv.declineDuel(player, true, true);
                        }
                    }
                }
            }.runTaskLater(CosmicDuel.get(), 900L);
        } else {
            final QueuePopInventory inv2 = new QueuePopInventory(this);
            inv2.prepareQueue();
            inv2.alertOfQueue();
            new BukkitRunnable(){

                public void run() {
                    if (!inv2.wasAccepted() && inv2.duel != null) {
                        DuelAPI.cancelDuel(inv2.duel, CancelReason.DENY_QUEUE_POP);
                    }
                }
            }.runTaskLater(CosmicDuel.get(), 200L);
        }
    }
}

