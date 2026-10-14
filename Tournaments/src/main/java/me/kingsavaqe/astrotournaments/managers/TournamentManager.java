package me.kingsavaqe.astrotournaments.managers;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import me.kingsavaqe.astrotournaments.objects.TournamentMatch;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.*;
import me.kingsavaqe.astrotournaments.utils.Queue;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.core.killtracker.KillTrackerAPI;
import net.astropvp.storage.StoragePlugin;
import net.astropvp.storage.utils.SaveLoadManager;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

@Getter
@Setter
public class TournamentManager extends SaveLoadManager<TournamentPlayer> {

    private final AstroTournamentsPlugin astroTournamentsPlugin;

    private final List<TournamentPlayer> tournamentParticipants;
    private final List<TournamentMatch> tournamentMatchList;
    public Queue<TournamentPlayer> playerQueue;


    private final List<SkinType> bannedSkins;
    private final List<ArmorSetType> currentArmorCrystal;
    private me.kingsavaqe.astrotournaments.tasks.TournamentStartTask startTask;
    private boolean active;
    private boolean joinable;
    private Location waitingRoomLocation;

    public TournamentManager(AstroTournamentsPlugin astroTournamentsPlugin) {
        super(astroTournamentsPlugin, StoragePlugin.getInstance().getGson(), "data/", TournamentPlayer.class);
        this.astroTournamentsPlugin = astroTournamentsPlugin;
        this.tournamentMatchList = new ArrayList<>();
        this.tournamentParticipants = new ArrayList<>();
        this.bannedSkins = loadBannedSkinsFromConfig();
        this.currentArmorCrystal = loadCurrentArmorCrystalFromConfig();
    }


    public void setupQueue() {
        tournamentParticipants.forEach(player -> {
            playerQueue.enqueue(player);
        });
    }

    public TournamentPlayer getTournamentPlayer(UUID uuid) {
        if (!contains(uuid.toString())) {
            TournamentPlayer tournamentPlayer = new TournamentPlayer(uuid);
            addToCache(tournamentPlayer);
        }
        return getFromCache(uuid.toString());
    }


    public void putNextPlayersInArenas() {

        Queue<TournamentPlayer> playersInTournament = getPlayerQueue();

        if (!active || joinable) return;
        if (tournamentParticipants.isEmpty()) { end(); return; }
        if (astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().size() == 1) {
            TournamentPlayer winnerTournamentPlayer = astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().get(0);
            Player winner = winnerTournamentPlayer.getPlayer();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(Utils.fixColor("&6&lAstro&f&lPvP &6&lTournaments"));
            Bukkit.broadcastMessage(Utils.fixColor(" &f&l* &fTournament Winner: &e" + winner.getName()));
            Bukkit.broadcastMessage("");
            astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().clear();
            astroTournamentsPlugin.getTournamentManager().getPlayerQueue().clear();
            astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().clear();
            astroTournamentsPlugin.getTournamentManager().setJoinable(false);
            astroTournamentsPlugin.getTournamentManager().setActive(false);
            winner.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            winnerTournamentPlayer.increaseWins();
            winnerTournamentPlayer.setInTournament(false);
            winnerTournamentPlayer.setPendingTournamentRewards(winnerTournamentPlayer.getPendingTournamentRewards() + 1);
            saveAll();
            deliverPendingRewards(winnerTournamentPlayer);
            return;
        }
        this.astroTournamentsPlugin.getArenaRegister().getArenas().values().forEach(arena -> {

            if (!arena.isInUse() && playersInTournament.size() >= 2) {

                TournamentPlayer player1 = playersInTournament.dequeue();
                TournamentPlayer player2 = playersInTournament.dequeue();
                arena.setCurrentlyFighting(new Pair<>(player1, player2));
                arena.setInUse(true);
                TournamentMatch match = new TournamentMatch(player1, player2, arena);
                this.tournamentMatchList.add(match);
                Bukkit.getScheduler().runTaskLater(astroTournamentsPlugin, ()-> {
                    if (!active || !tournamentMatchList.contains(match)) return;
                    setupPlayer(player1, arena.getLocations().getLeft(), match);
                    setupPlayer(player2, arena.getLocations().getRight(), match);
                }, 40L);
            }
        });

    }

    public void deliverPendingRewards(TournamentPlayer participant) {
        Player player = participant.getPlayer();
        if (player == null || participant.getPendingTournamentRewards() <= 0) return;
        if (CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("TournamentLootbox") == null) {
            astroTournamentsPlugin.getLogger().warning("Original TournamentLootbox definition is missing; retaining pending reward for " + participant.getUuid());
            return;
        }
        while (participant.getPendingTournamentRewards() > 0) {
            ItemStack reward = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("TournamentLootbox").createClaimableItem(false);
            if (player.getInventory().firstEmpty() == -1) return;
            player.getInventory().addItem(reward);
            participant.setPendingTournamentRewards(participant.getPendingTournamentRewards() - 1);
            player.saveData();
            saveAll();
        }
    }

    private void setupPlayer(TournamentPlayer tournamentPlayer, Location location, TournamentMatch match) {
        Player player = tournamentPlayer.getPlayer();
        if (player == null || !player.isOnline()) return;
        player.closeInventory();
        player.teleport(location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        player.setHealth(20);
        player.setFlying(false);
        player.setAllowFlight(false);
        Bukkit.getScheduler().runTaskLater(astroTournamentsPlugin, () -> {
            if (!active || !tournamentMatchList.contains(match) || tournamentPlayer.getPlayer() == null) return;
            tournamentPlayer.getPlayer().sendMessage(CC.translate("&a&l(!)&a You have been given your tournament set! Good Luck!"));
            giveItems(tournamentPlayer);
        }, 20L);
        Bukkit.getScheduler().runTaskLater(astroTournamentsPlugin, () -> {
            if (active && tournamentMatchList.contains(match) && tournamentPlayer.getPlayer() != null)
                Blessed.blessPlayer(tournamentPlayer.getPlayer());
        }, 100L);
    }

    private void giveItems(TournamentPlayer tournamentPlayer) {
        Player player = tournamentPlayer.getPlayer();
        Potion splash = new Potion(PotionType.INSTANT_HEAL, 2);
        splash.setSplash(true);

        ItemStack stackedPot = splash.toItemStack(8);

        player.getInventory().setItem(8, tournamentPlayer.getBoots());
        player.getInventory().setItem(7, tournamentPlayer.getChestplate());
        player.getInventory().setItem(6, tournamentPlayer.getLeggings());
        player.getInventory().setItem(5, tournamentPlayer.getHelmet());
        player.getInventory().setItem(0, tournamentPlayer.getSword());
        player.getInventory().setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        player.getInventory().setItem(2, KillTrackerAPI.createSoulShard(250000));
        player.getInventory().setItem(27, new NBTWrapper(stackedPot).setString("cosmicType", "stackable").setNBTBase("conaxData", new NBTTagCompoundBuilder().setInt("maxStackSize", 8).setLong("uniquePots", Long.valueOf(System.nanoTime())).get()).build());
        player.getInventory().setItem(28, new NBTWrapper(stackedPot).setString("cosmicType", "stackable").setNBTBase("conaxData", new NBTTagCompoundBuilder().setInt("maxStackSize", 8).setLong("uniquePots", Long.valueOf(System.nanoTime())).get()).build())
        ;
        player.getInventory().setItem(29, new NBTWrapper(stackedPot).setString("cosmicType", "stackable").setNBTBase("conaxData", new NBTTagCompoundBuilder().setInt("maxStackSize", 8).setLong("uniquePots", Long.valueOf(System.nanoTime())).get()).build())
        ;
        player.getInventory().setItem(30, new NBTWrapper(stackedPot).setString("cosmicType", "stackable").setNBTBase("conaxData", new NBTTagCompoundBuilder().setInt("maxStackSize", 8).setLong("uniquePots", Long.valueOf(System.nanoTime())).get()).build())
        ;
        player.getInventory().setItem(31, new NBTWrapper(stackedPot).setString("cosmicType", "stackable").setNBTBase("conaxData", new NBTTagCompoundBuilder().setInt("maxStackSize", 8).setLong("uniquePots", Long.valueOf(System.nanoTime())).get()).build())
        ;


        while (Utils.hasAvaliableSlot(player, 1)) {
            ItemStack splashPot = splash.toItemStack(1);
            player.getInventory().addItem(splashPot);
        }
    }

    public boolean isBannedItemSkin(SkinType skinType) {
        return bannedSkins.contains(skinType);
    }

    public void banItemSkin(SkinType skinType, Player player) {
        if (!bannedSkins.contains(skinType)) {
            player.sendMessage(CC.translate("&c&l(!)&c Banned the " + skinType.getItem().getItemMeta().getDisplayName()));
            bannedSkins.add(skinType);
        } else {
            player.sendMessage(CC.translate("&a&l(!)&a UnBanned the " + skinType.getItem().getItemMeta().getDisplayName()));
            bannedSkins.remove(skinType);
        }
    }

    public List<SkinType> loadBannedSkinsFromConfig() {
        List<SkinType> bannedSkins = new ArrayList<>();
        return bannedSkins;
        //Load the banned skins
    }

    public void saveBannedSkinsToConfig() {
        //Save banned skins
    }

    public List<ArmorSetType> loadCurrentArmorCrystalFromConfig() {
        List<ArmorSetType> currentCrystal = new ArrayList<>();
        return currentCrystal;
        //load the current crystal
    }

    public void saveCurrentArmorCrystalToConfig() {
        //save the current crystal
    }

    public void addArmorCrystalToCurrentCrystal(ArmorSetType armorSetType, Player player) {
        if (currentArmorCrystal.contains(armorSetType)) {
            currentArmorCrystal.remove(armorSetType);
            player.sendMessage(CC.translate("&c&l(!)&c Removed the " + armorSetType.getDisplay() + " &cCrystal from the current tournament crystal"));
        } else {
            currentArmorCrystal.add(armorSetType);
            player.sendMessage(CC.translate("&a&l(!)&a Added the " + armorSetType.getDisplay() + " &aCrystal to the current tournament crystal"));

        }
    }

    /** One idempotent exit path for death, leaving, disconnect and kick. */
    public void eliminate(TournamentPlayer loser, boolean died) {
        if (!loser.isInTournament() && !tournamentParticipants.contains(loser)) return;
        TournamentMatch match = null;
        for (TournamentMatch candidate : tournamentMatchList)
            if (candidate.getPlayerOne() == loser || candidate.getPlayerTwo() == loser) { match = candidate; break; }
        loser.setInTournament(false);
        if (loser.getPlayer() != null) loser.getPlayer().sendMessage(Utils.fixColor("&cYou have been eliminated from the tournament"));
        tournamentParticipants.remove(loser);
        playerQueue.removeFromQueue(loser);
        if (loser.getPlayer() != null) Utils.emptyInventory(loser.getPlayer());
        if (match == null) {
            if (active && !joinable) putNextPlayersInArenas();
            return;
        }
        TournamentPlayer winner = match.getPlayerOne() == loser ? match.getPlayerTwo() : match.getPlayerOne();
        tournamentMatchList.remove(match);
        match.getArena().reset();
        for (TournamentPlayer participant : tournamentParticipants) {
            if (participant.getPlayer() != null)
                participant.getPlayer().sendMessage(Utils.fixColor("&6Tournaments &8| &7" + (winner.getPlayer() == null ? winner.getUuid().toString() : winner.getPlayer().getName()) + " &ehas beaten the player &7" + (loser.getPlayer() == null ? loser.getUuid() : loser.getPlayer().getName()) + " &ein a tournament match"));
        }
        if (died) { winner.increaseKills(); loser.increaseDeaths(); loser.increaseLosses(); }
        if (winner.getPlayer() != null) {
            Utils.emptyInventory(winner.getPlayer());
            winner.getPlayer().setFireTicks(0);
            org.arkhamnetwork.enchantments.util.GeneralUtil.healPlayer(winner.getPlayer(), winner.getPlayer().getMaxHealth());
            winner.getPlayer().teleport(waitingRoomLocation, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }
        playerQueue.removeFromQueue(winner);
        playerQueue.enqueue(winner);
        Bukkit.getScheduler().runTaskLater(astroTournamentsPlugin, () -> {
            if (active && !joinable) putNextPlayersInArenas();
        }, 20L);
    }

    public void end() {
        if (startTask != null) { startTask.cancel(); startTask = null; }
        astroTournamentsPlugin.getArenaRegister().getArenas().values().forEach(arena -> arena.reset());
        for (TournamentPlayer tournamentPlayer : getTournamentParticipants()) {

            tournamentPlayer.setInTournament(false);
            if (tournamentPlayer.getPlayer() == null) continue;
            Utils.emptyInventory(tournamentPlayer.getPlayer());
            tournamentPlayer.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            tournamentPlayer.getPlayer().sendMessage(CC.translate("&c&l(!)&c The tournament has been force ended and you have been teleported to spawn!"));
        }
        astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().clear();
        astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().clear();
        astroTournamentsPlugin.getTournamentManager().getPlayerQueue().clear();


        astroTournamentsPlugin.getTournamentManager().setActive(false);
        astroTournamentsPlugin.getTournamentManager().setJoinable(false);
    }


}
