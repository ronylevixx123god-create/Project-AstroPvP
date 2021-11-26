/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
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
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
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
import me.ifamasssxd.killtracker.KillTrackerAPI;
import net.astropvp.core.utils.TitleUtil;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.listeners.PlayerPotionEffectListener;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;
import org.yaml.snakeyaml.events.Event;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassicDuel extends NewDuel {
    public ClassicDuel(Player initiator, List<String> pendingInvites) {
        super(initiator, pendingInvites);
        this.ending = false;
        this.format = new DecimalFormat("#.#");
        this.duelID = id++;
    }

    public ClassicDuel(Player initiator, List<String> pendingInvites, boolean ranked) {
        super(initiator, pendingInvites, ranked);
        this.ending = false;
        this.format = new DecimalFormat("#.#");
        this.duelID = id++;
    }

    public String getName() {
        return "1v1";
    }

    public DuelType getDuelType() {
        return DuelType.ONE_VS_ONE;
    }

    public int getPlayersToStart() {
        return 2;
    }

    public void addPlayer(Player player, boolean checkStart) {
        this.players.add(player);
        player.setMetadata("in_duel", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
        if (checkStart && this.players.size() >= getPlayersToStart()) {
            setCreateState(DuelCreateState.STAKES);
            this.stakeInventory = new NewStakeInventory(this, getPlayers().get(0), player);
            for (Player p : getPlayers()) {
                p.setMetadata("close_exempt", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
                p.closeInventory();
                if (p.getUniqueId().equals(this.stakeInventory.getP1().getUniqueId())) {
                    p.openInventory(this.stakeInventory.getPlayerOneInventory());
                    continue;
                }
                p.openInventory(this.stakeInventory.getPlayerTwoInventory());
            }
        }
    }

    private void removeVanish(Player player) {
        if (VanishUtils.isEssentialsVanished(player)) {
            VanishUtils.toggleEssentialsVanished(player, false);
            player.sendMessage(CC.translate("&6Your vanish has been &cremoved &6since you are in a duel."));
        }
    }

    private void setupPlayer(Player player) {
        CosmicDuel.logMessage("Preparing " + player.getName() + " for duel " + this.duelID);
        this.storedPlayers.put(player.getUniqueId(), new StoredPlayer(player));
        this.originalLocation.put(player.getUniqueId(), player.getLocation());
        player.setMetadata("in_duel", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
        Location spawn = checkIfSpawnLoaded(false);
        forceTeleport(player, spawn);
        System.out.println("[CosmicDuels] Teleporting " + player.getName() + " to " + spawn);
        clearChat(player);
        resetPlayerStats(player);
        applySettings(player);
        checkMcmmoParty(player);
        removeVanish(player);
    }

    public void onStart() {
        setDuelState(DuelState.STARTING);
        SpectateInventory.updateInventory();
        for (Player p : getPlayers())
            setupPlayer(p);
        clearArena();
        for (int i = 0; i < this.players.size(); i++) {
            if (getDuelType() == DuelType.ONE_VS_ONE) {
                Player p = this.players.get(i);
                Player pl = getPlayers().get((i == getPlayers().size() - 1) ? 0 : (i + 1));
                p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "Your " + (isRanked() ? (ChatColor.YELLOW + ChatColor.BOLD.toString() + "RANKED ") : "") + ChatColor.YELLOW + "duel with " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + pl.getName() + ChatColor.YELLOW + " will begin shortly...");
            }
        }
        final NewDuel duel = this;
        (new BukkitRunnable() {
            AtomicInteger count = new AtomicInteger(10);

            public void run() {
                if (ClassicDuel.this.getPlayers().size() < ClassicDuel.this.getPlayersToStart()) {
                    cancel();
                    DuelAPI.cancelDuel(duel, CancelReason.NOT_ENOUGH_PLAYERS);
                    return;
                }
                if (this.count.decrementAndGet() <= 0) {
                    cancel();
                    CosmicDuel.logMessage("Starting duel in arena " + ClassicDuel.this.arena.getName() + " with id " + ClassicDuel.this.duelID + " settings -> " + ClassicDuel.this.getSettings().toString());
                    ClassicDuel.this.start();
                    return;
                }
                for (Player p : ClassicDuel.this.getPlayers()) {
                    p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "The duel will start in " + ChatColor.YELLOW + ChatColor.BOLD.toString() + this.count.get() + "s.");
                    p.playNote(p.getLocation(), Instrument.PIANO, new Note(3));
                }
            }
        }).runTaskTimer((Plugin)CosmicDuel.get(), 20L, 20L);
    }

    public void onDuelEnd(EndReason endReason, Player loser) {
        this.ending = true;
        resetEnvoys();
        CosmicDuel.logMessage("Ending duel for reason: " + endReason.name() + " loser: " + ((loser != null) ? loser.getName() : "none"));
        if (endReason == EndReason.WIN) {
            if (this.players.size() >= 1) {
                Player winner = this.players.get(0);
                String winningMsg = getColoredMessage("&e&l(!) &a&n%s&e has defeated %s!", new Object[] { winner.getName(), loser.getName() });
                JSONMessage jsonMessage = new JSONMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Inventories: ");
                sendMessage("", true);
                sendMessage(winningMsg, true);
                if (!getEndingInventoryCache().containsKey(winner.getName())) {
                    UUID uuid = CosmicDuel.get().getDuelHistoryManager().storeInventorySnapshot(winner);
                    getEndingInventoryCache().put(winner.getName(), uuid);
                }
                int index = 0;
                for (Map.Entry<String, UUID> invs : getEndingInventoryCache().entrySet()) {
                    boolean includeComma = true;
                    if (index++ == getEndingInventoryCache().size() - 1)
                        includeComma = false;
                    jsonMessage.addRunCommand((String)invs.getKey() + (includeComma ? ", " : ""), winner.getName().equals(invs.getKey()) ? ChatColor.GREEN : ChatColor.RED, "/viewdeathinventory " + ((UUID)getEndingInventoryCache().get(invs.getKey())).toString(), ChatColor.GRAY + "Click to view duel inventory.");
                }
                sendJsonMessage(jsonMessage, true);
                sendMessage("", true);
                if (loser != null) {
                    loser.sendMessage("");
                    loser.sendMessage(winningMsg);
                    jsonMessage.sendToPlayer(loser);
                    loser.sendMessage("");
                }
                removePlayer(winner, Reason.WIN, false);
                winner.playSound(winner.getLocation(), Sound.LEVEL_UP, 1.0F, 1.4F);
                RandomFireworks.getManager().launchRandomFirework(winner.getLocation());
                awardStakes(winner);
                if (this.settings.hasSetting(Setting.DEATH_CERT))
                    giveWinnerPage(winner, loser);
                if (this.settings.hasSetting(Setting.DROPITEMS) && this.kit.equals(Kit.BYOB)) {
                    CosmicDuel.logMessage("Dropping all of " + loser.getName() + "'s Items to " + winner.getName());
                    List<ItemStack> items = Lists.newArrayList();
                    for (ItemStack armor : loser.getInventory().getArmorContents()) {
                        if (armor != null && armor.getType() != Material.AIR)
                            items.add(armor);
                    }
                    loser.getInventory().setArmorContents(null);
                    removePotionEffects(loser);
                    for (ListIterator<ItemStack> listIterator = loser.getInventory().iterator(); listIterator.hasNext(); ) {
                        ItemStack is = listIterator.next();
                        if (is != null && is.getType() != Material.AIR)
                            items.add(is);
                    }
                    loser.getInventory().clear();
                    loser.updateInventory();
                    CollectionBin.addItems(winner, items);
                    winner.sendMessage(CC.translate("&a&l(!) &aUse &n/duel collect&a to collect your winnings!"));
                    winner.sendMessage(CC.translate("&7You can use &f/duel collect removepotions &7to remove all potions from your winnings."));
                }
                if (isRanked()) {
                    winner.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You defeated " + loser.getName() + " in a Ranked Duel!");
                    loser.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You have been defeated by " + winner.getName() + " in a Ranked Duel!");
                }
                DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(winner);
                DuelPlayer loserPlayer = DuelPlayer.getDuelPlayer(loser);
                if (duelPlayer != null && loserPlayer != null) {
                    if (isRanked()) {
                        double biggest = Math.max(duelPlayer.getElo(), loserPlayer.getElo());
                        double lowest = Math.min(duelPlayer.getElo(), loserPlayer.getElo());
                        double extraWin = (1.0D - lowest / biggest) * 2.0D * 10.0D;
                        double toGain = Math.max(1.0D, (duelPlayer.getElo() > loserPlayer.getElo()) ? (10.0D - extraWin) : (10.0D + extraWin)) * 5.0D;
                        double extraLoss = (1.0D - lowest / biggest) * 2.0D * 8.0D;
                        double toLose = Math.max(1.0D, (loserPlayer.getElo() < duelPlayer.getElo()) ? (8.0D - extraLoss) : (8.0D + extraLoss)) * 5.0D;
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
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "contest add " + winner.getName() + " rankedduels 1");
                    } else {
                        duelPlayer.setWins(duelPlayer.getWins() + 1);
                        loserPlayer.setLosses(loserPlayer.getLosses() + 1);
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "contest add " + winner.getName() + " unrankedduels 1");
                    }
                    loserPlayer.saveRankedStats(true);
                    duelPlayer.saveRankedStats(true);
                    Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(winner, "DUEL_WIN", new Object[] { Boolean.valueOf(isRanked()), loser }));
                    Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(loser, "DUEL_LOSE", new Object[] { Boolean.valueOf(isRanked()), winner }));
                }
                winner.sendMessage("");
                loser.sendMessage("");
                Title titleWinner = new Title(CC.translate("&a&lWinner"), CC.translate("&7") + winner.getName(), 10, 40, 10);
                winner.sendTitle(titleWinner);
                Title titleLoser = new Title(CC.translate("&c&lYou Lost"), CC.translate("&7Winner: ") + winner.getName(), 10, 40, 10);
                winner.sendTitle(titleWinner);
                loser.sendTitle(titleLoser);            }
        } else if (endReason == EndReason.RELOAD || endReason == EndReason.NO_WINNER) {
            onEnd(false);
            returnStakes();
            DuelAPI.cancelDuel(this, CancelReason.RELOAD);
            return;
        }
        this.duelState = DuelState.ENDING;
        (new BukkitRunnable() {
            int count = 5;

            public void run() {
                int count = this.count - 1;
                this.count = count;
                if (count <= 0) {
                    cancel();
                    ClassicDuel.this.onEnd(true);
                    return;
                }
                ClassicDuel.this.sendMessage(ClassicDuel.this.getColoredMessage("&c&l(!) &cThe duel will end in %s.", new Object[] { Integer.valueOf(this.count) }), true);
            }
        }).runTaskTimer((Plugin)CosmicDuel.get(), 20L, 20L);
    }

    public Inventory getDroppedItemsInventory(Player loser) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, loser.getInventory().getSize() + 9, loser.getName() + "'s Death Loot");
        int index = 0;
        for (ItemStack armor : loser.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR)
                inv.setItem(index, armor);
            index++;
        }
        loser.getInventory().setArmorContents((ItemStack[])null);
        removePotionEffects(loser);
        index = 9;
        for (ListIterator<ItemStack> listIterator = loser.getInventory().iterator(); listIterator.hasNext(); ) {
            ItemStack is = listIterator.next();
            if (is != null && is.getType() != Material.AIR)
                inv.setItem(index, is);
            index++;
        }
        loser.getInventory().clear();
        loser.updateInventory();
        return inv;
    }

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

            ItemStack is = ItemBuilder.buildItem(Material.PAPER, ChatColor.WHITE + ChatColor.BOLD.toString() + loser.getName() + "'s Death Certificate", new String[] { ChatColor.WHITE + loser.getName() + ChatColor.GRAY + " was defeated in a " + ChatColor.WHITE + getName(), ChatColor.GRAY + "duel on " + ChatColor.WHITE + format.format(date) + " at " + timeFormat.format(date), ChatColor.GRAY + "in the " + ChatColor.WHITE + getArena().getName() + " Arena " + ChatColor.GRAY + "by " + ChatColor.WHITE + winner.getName(), (extraKitName != null) ? extraKitName : null });
            if (winner.getInventory().firstEmpty() == -1) {
                CollectionBin.addItem(winner, is);
            } else {
                winner.getInventory().addItem(new ItemStack[] { is });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void awardBounty(Player winner, Player loser) {}



    private void clearArena() {
        for (Entity e : getArena().getSpawnLocation(true).getWorld().getEntities()) {
            if (e instanceof Player)
                continue;
            if (!WorldGuardUtils.getRegionName(e.getLocation()).toLowerCase().startsWith(getArena().getRegion().toLowerCase()))
                continue;
            e.remove();
        }
    }

    private void checkMcmmoParty(Player p) {
        if (!CosmicDuel.mcMMOEnabled)
            return;
        if (this.settings.hasSetting(Setting.MCMMO) && PartyAPI.inParty(p)) {
            String name = PartyAPI.getPartyName(p);
            PartyAPI.removeFromParty(p);
            p.setMetadata("mcmmo_party", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), name));
        }
    }

    private void clearChat(Player p) {
        for (int i = 0; i < 35; i++)
            p.sendMessage("");
    }

    private Location checkIfSpawnLoaded(boolean random) {
        Location retr = getArena().getSpawnLocation(random);
        if (retr.getChunk() != null && !retr.getChunk().isLoaded())
            retr.getChunk().load();
        return retr;
    }

    public void start() {
        this.duelState = DuelState.PLAYING;
        for (Player p : getPlayers()) {
            p.setHealth(p.getMaxHealth());
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 0.6F, 1.0F);
            p.sendMessage(getColoredMessage("&e&lFIGHT!"));

            RandomFireworks.getManager().launchRandomFirework(p.getLocation());
            p.chat("/apply");
            Blessed.blessPlayer(p, true);
        }
    }

    public void onEnd(boolean cancelDuel) {
        this.duelState = DuelState.ENDED;
        for (Object pl1 : Lists.newArrayList(getPlayers())) {
            Player pl = (Player)pl1;
            removePlayer(pl, Reason.WIN, false);
        }
        clearSpectators();
        if (cancelDuel)
            DuelAPI.cancelDuel(this, CancelReason.DUEL_END);
    }

    public void removePlayer(Player player, Reason reason, boolean createSpectator) {
        if (!this.players.contains(player))
            return;
        CosmicDuel.logMessage("Removing " + player.getName() + " from duel ID: " + this.duelID);
        this.players.remove(player);
        player.setMetadata("lastDuelEnd", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick())));
        player.removeMetadata("in_duel", (Plugin)CosmicDuel.get());
        if (reason != Reason.WIN)
            sendMessage(reason.getReason(player), true);
        if (!getEndingInventoryCache().containsKey(player.getName())) {
            UUID uuid = CosmicDuel.get().getDuelHistoryManager().storeInventorySnapshot(player);
            getEndingInventoryCache().put(player.getName(), uuid);
        }
        resetPlayer(player);
        removePotionEffects(player);
        if (!this.ending && createSpectator && !willDuelEnd()) {
            prepareSpectator(player);
        } else {
            resetMcMMO(player);
            returnPlayerToStartLocation(player);
        }
        returnItems(player, (reason == Reason.RELOAD));
        if (reason == Reason.WIN || reason == Reason.RELOAD || reason == Reason.NO_WINNER)
            return;
        if (getCreateState() != DuelCreateState.DONE)
            return;
        if (willDuelEnd() && isCurrentlyInDuel())
            onDuelEnd(EndReason.WIN, player);
    }

    public boolean willDuelEnd() {
        return (this.players.size() <= 1);
    }

    public void resetPlayerStats(Player p) {
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setFireTicks(0);
        p.setFoodLevel(20);
        p.setFallDistance(0.0F);
        p.setHealth(p.getMaxHealth());
        p.spigot().setCollidesWithEntities(true);
        if (CosmicDuel.mcMMOEnabled)
            BleedTimerTask.remove((LivingEntity)p);
        if (!VanishUtils.isVanished(p))
            for (Player pl : Bukkit.getOnlinePlayers())
                pl.showPlayer(p);
    }

    public void returnItems(Player p, boolean reloading) {
        if (!this.storedPlayers.containsKey(p.getUniqueId()))
            return;
        StoredPlayer player = this.storedPlayers.get(p.getUniqueId());
        player.restorePlayer(p, reloading);
        this.storedPlayers.remove(p.getUniqueId());
    }

    public void returnPlayerToStartLocation(Player p) {
        if (!DuelAPI.isRebooting() && MinecraftServerUtils.isRunning()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), () -> {
                CombatLog.unflagCombat(p);
                if (p.hasMetadata("cl_combat"))
                    p.removeMetadata("cl_combat", (Plugin)Arkkit.get());
            },5L);
        } else {
            CombatLog.unflagCombat(p);
        }
        if (this.originalLocation.containsKey(p.getUniqueId()))
            forceTeleport(p, this.originalLocation.get(p.getUniqueId()));
        this.originalLocation.remove(p.getUniqueId());
    }

    public void resetMcMMO(Player p) {
        if (!CosmicDuel.mcMMOEnabled)
            return;
        if (!this.settings.hasSetting(Setting.MCMMO))
            p.removeMetadata("no_mcmmo_skills", (Plugin)CosmicDuel.get());
        if (p.hasMetadata("mcmmo_party") && p.getMetadata("mcmmo_party").size() > 0) {
            String name = ((MetadataValue)p.getMetadata("mcmmo_party").get(0)).asString();
            if (PartyAPI.inParty(p))
                return;
            PartyAPI.addToParty(p, name);
        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Player> getSpectators() {
        return this.spectators;
    }

    public void prepareSpectator(Player spectator) {
        if (getDuelState() == DuelState.ENDING || getDuelState() == DuelState.IN_QUEUE)
            return;
        if (this.spectators.contains(spectator))
            return;
        spectator.setMetadata("spectator", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
        this.spectators.add(spectator);
        resetPlayerStats(spectator);
        for (Player pl : Bukkit.getOnlinePlayers())
            pl.hidePlayer(spectator);
        spectator.spigot().setCollidesWithEntities(false);
        if (!this.originalLocation.containsKey(spectator.getUniqueId()))
            this.originalLocation.put(spectator.getUniqueId(), spectator.getLocation());
        Title titleWinner = new Title(CC.translate("&a&lSPECTATOR"), CC.translate("&7You are now spectating the duel."), 10, 40, 10);
        spectator.sendTitle(titleWinner);
        spectator.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You are now " + ChatColor.GREEN + ChatColor.UNDERLINE + "SPECTATING" + ChatColor.GREEN + " this duel.");
        spectator.sendMessage(ChatColor.GRAY + "You can use " + ChatColor.YELLOW + "'/duel leave'" + ChatColor.GRAY + " to stop spectating.");
        forceTeleport(spectator, getArena().getSpawnLocation(true));
        for (Player spec : getSpectators())
            spectator.hidePlayer(spec);
        spectator.setAllowFlight(true);
        spectator.setFlying(true);
    }

    public void removeSpectator(Player p) {
        p.removeMetadata("spectator", (Plugin)CosmicDuel.get());
        if (!this.spectators.contains(p))
            return;
        this.spectators.remove(p);
        resetPlayer(p);
        resetMcMMO(p);
        returnPlayerToStartLocation(p);
        if (p.hasPotionEffect(PotionEffectType.POISON))
            p.removePotionEffect(PotionEffectType.POISON);
    }

    public void clearSpectators() {
        for (Player pl : getSpectators())
            removeSpectator(pl);
    }

    public void resetPlayer(Player p) {
        resetPlayerStats(p);
    }

    public boolean isCurrentlyInDuel() {
        return (getDuelState().isValid() && getCreateState() == DuelCreateState.DONE);
    }

    public Player getOppositePlayer(Player p) {
        if (getPlayers().size() == 2) {
            if (p.equals(getPlayers().get(0)))
                return getPlayers().get(1);
            if (p.equals(getPlayers().get(1)))
                return getPlayers().get(0);
        }
        return null;
    }

    public void applySettings(Player p) {
        if (!this.settings.hasSetting(Setting.MCMMO) && CosmicDuel.mcMMOEnabled)
            p.setMetadata("no_mcmmo_skills", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
        if (this.kit != Kit.BYOB) {
            wipePlayer(p);
            applyKit(p);
        } else {
            boolean hadDisabledItems = false;
            for (ListIterator<ItemStack> listIterator = p.getInventory().iterator(); listIterator.hasNext(); ) {
                ItemStack is = listIterator.next();
                if (is != null && is.getType() != Material.AIR && (isDisabledDuelItem(is) || !isValidItem(is))) {
                    p.getInventory().removeItem(new ItemStack[] { is });
                    hadDisabledItems = true;
                }
            }
            p.updateInventory();
            if (!this.settings.hasSetting(Setting.ARMOR)) {
                for (ItemStack armor : p.getInventory().getArmorContents()) {
                    if (armor != null && armor.getType() != Material.AIR) {
                        CosmicDuel.logMessage("Removing armor for duel " + p.getName());
                        p.sendMessage(ChatColor.RED + "Your armor has been stored and will be returned after the duel ends.");
                        break;
                    }
                }
                p.getInventory().setArmorContents((ItemStack[])null);
                p.updateInventory();
            }
            if (hadDisabledItems) {
                p.sendMessage(getColoredMessage("&c&l(!) &cYou had items that were disabled in this duel."));
                p.sendMessage(ChatColor.GRAY + "They will be returned to you once the duel is over.");
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), () -> {
            if (p.isValid())
                PlayerPotionEffectListener.rescanPlayerArmor(p);
        },4L);
    }

    public void applyKit(Player p) {
        List<ItemStack> list;
        DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(p);
        if (isRanked()) {
            list = duelPlayer.getCustomGodSet().getKitItems(true, false);
            list.add(new ItemStack(Material.ENDER_PEARL, 16));
            list.add(new ItemStack(Material.ARROW, 64));
            list.add(new ItemStack(Material.GOLDEN_APPLE, 1));
        } else {
            list = getKit().getItems();
        }
        boolean hadDisabledItems = false;
        int potionsToAdd = 0;
        for (ItemStack is : list) {
            if (isDisabledDuelItem(is)) {
                potionsToAdd++;
                hadDisabledItems = true;
                continue;
            }
            if (isRanked() && EnchantmentAPI.getEnchantments(is).size() > 0)
                is = EnchantmentBook.applyTransmogScroll(is.clone());
            is = KitUtils.addDuelWatermark(is.clone());
            if ((is.getType() == Material.POTION || is.getType() == Material.MUSHROOM_SOUP) && is.getAmount() > 1) {
                while (is.getAmount() > 0) {
                    ItemStack toAdd = is.clone();
                    toAdd.setAmount(1);
                    is.setAmount(is.getAmount() - 1);
                    p.getInventory().addItem(new ItemStack[] { toAdd });
                }
                continue;
            }
            p.getInventory().addItem(new ItemStack[] { is });
        }
        if (isRanked()) {
            ItemStack potion = new ItemStack(Material.POTION, 1, (short)16421);
            potion = KitUtils.addDuelWatermark(potion);
            for (int i = 0; i < p.getInventory().getSize(); i++) {
                if (p.getInventory().getItem(i) == null || p.getInventory().getItem(i).getType() == Material.AIR)
                    p.getInventory().setItem(i, potion);
            }
        }
        for (int j = 0; j < potionsToAdd; j++) {
            p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.POTION, 1, (short)16421) });
        }
        p.updateInventory();
        if (hadDisabledItems)
            p.sendMessage(getColoredMessage("&e&l(!) &eYour kit contained some items that were &e&ndisabled&e in this duel."));
    }

    public void wipePlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents((ItemStack[])null);
        p.updateInventory();
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), () -> removePotionEffects(p), 7L);
    }

    public void removePotionEffects(Player p) {
        for (PotionEffect pe : p.getActivePotionEffects())
            p.removePotionEffect(pe.getType());
    }

    public boolean isDisabledDuelItem(ItemStack is) {
        if (KillTrackerAPI.isSoulGem(is))
            return !getSettings().hasSetting(Setting.SOUL_GEM);
        if (is.getType() == Material.SKULL_ITEM) {
            NBTWrapper wrapper = new NBTWrapper(is);
            if (wrapper.getCompoundOrCreate("cosmicData").hasKey("petType"))
                return !getSettings().hasSetting(Setting.PET);
        }
        if (DuelUtils.isArmor(is))
            return !getSettings().hasSetting(Setting.ARMOR);
        if (DuelUtils.isWeapon(is) && is.getType() != Material.BOW)
            return !getSettings().hasSetting(Setting.WEAPONS);
        switch (is.getType()) {
            case GOLDEN_APPLE:
                return !getSettings().hasSetting(Setting.GOLDEN_APPLES);
            case ENDER_PEARL:
                return !getSettings().hasSetting(Setting.ENDER_PEARLS);
            case BOW:
            case ARROW:
                return !getSettings().hasSetting(Setting.BOWS);
            case POTION:
                return !getSettings().hasSetting(Setting.POTIONS);
            case COOKED_BEEF:
            case GOLDEN_CARROT:
                return !getSettings().hasSetting(Setting.FOOD_LOSS);
        }
        return false;
    }

    private boolean isValidItem(ItemStack is) {
        return (DuelUtils.isArmor(is) || DuelUtils.isWeapon(is) || is.getType() == Material.POTION || is.getType() == Material.SKULL_ITEM || is.getType() == Material.GOLDEN_APPLE || is.getType() == Material.EMERALD || is.getType() == Material.MUSHROOM_SOUP || is.getType() == Material.ARROW || is.getType() == Material.ENDER_PEARL);
    }

    public void awardStakes(Player winner) {
        if (winner == null) {
            returnStakes();
            return;
        }
        boolean collectionBin = false;
        for (List<ItemStack> stakeItems : (Iterable<List<ItemStack>>)this.stakes.values()) {
            for (ItemStack stakeItem : stakeItems) {
                if (winner.getInventory().firstEmpty() == -1) {
                    CollectionBin.addItem(winner, stakeItem);
                    collectionBin = true;
                    continue;
                }
                winner.getInventory().addItem(new ItemStack[] { stakeItem });
            }
        }
        this.stakes.clear();
        winner.updateInventory();
        if (collectionBin)
            winner.sendMessage(getColoredMessage("&e&l(!) &eYour inventory was full and the stakes have been placed in your &a&n/duel collect&e bin."));
    }

    public void returnStakes() {
        System.out.println("G");
        for (Map.Entry<UUID, List<ItemStack>> item : (Iterable<Map.Entry<UUID, List<ItemStack>>>)this.stakes.entrySet()) {
            System.out.println("H");
            Player p = Bukkit.getPlayer(item.getKey());
            if (p != null) {
                System.out.println("I");
                boolean collectionBinUsed = false;
                for (ItemStack is : item.getValue()) {
                    if (p.getInventory().firstEmpty() == -1) {
                        CollectionBin.addItem(p, is);
                        collectionBinUsed = true;
                        continue;
                    }
                    p.getInventory().addItem(new ItemStack[] { is });
                }
                if (!collectionBinUsed)
                    continue;
                p.sendMessage("&e&l(!) &eYour inventory was full and the stakes have been placed in your &e&n/duel collect&e bin.");
            }
        }
        this.stakes.clear();
    }

    public void resetEnvoys() {
        for (Location l : this.envoys) {
            if (CosmicSpaceChestsPlugin.chest_locations.containsKey(l))
                CosmicSpaceChestsPlugin.chest_locations.remove(l);
            if (l.getBlock().getType() == Material.CHEST)
                DuelListener.removeChest(l);
        }
        this.envoys.clear();
    }

    public void onConfirmedQueue() {
        setCreateState(DuelCreateState.DONE);
        onStart();
    }

    public void onQueuePop() {
        this.inQueueMenu = true;
        if (isRanked()) {
            final GlobalQueuePopInventory inv = (new GlobalQueuePopInventory(this)).prepareQueue();
            inv.alertOfQueue();
            (new BukkitRunnable() {
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
            }).runTaskLater((Plugin)CosmicDuel.get(), 900L);
        } else {
            final QueuePopInventory inv2 = new QueuePopInventory(this);
            inv2.prepareQueue();
            inv2.alertOfQueue();
            (new BukkitRunnable() {
                public void run() {
                    if (!inv2.wasAccepted() && inv2.duel != null)
                        DuelAPI.cancelDuel(inv2.duel, CancelReason.DENY_QUEUE_POP);
                }
            }).runTaskLater((Plugin)CosmicDuel.get(), 200L);
        }
    }

    private static int id = 1;

    private boolean ending;

    private DecimalFormat format;
}
