package me.kingsavaqe.astrotournaments.managers;

import lombok.Getter;
import lombok.Setter;
import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import me.kingsavaqe.astrotournaments.objects.TournamentMatch;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.*;
import me.kingsavaqe.astrotournaments.utils.Queue;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

@Getter
@Setter
public class TournamentManager {

    private final AstroTournamentsPlugin astroTournamentsPlugin;
    private final List<TournamentPlayer> tournamentParticipants;
    private final List<TournamentMatch> tournamentMatchList;
    private final Map<UUID, TournamentPlayer> tournamentPlayers;
    private boolean active;
    private boolean joinable;
    public Queue<TournamentPlayer> playerQueue;
    private Location waitingRoomLocation;

    public TournamentManager(AstroTournamentsPlugin astroTournamentsPlugin) {
        this.astroTournamentsPlugin = astroTournamentsPlugin;
        this.tournamentPlayers = new HashMap<>();
        this.tournamentMatchList = new ArrayList<>();
        this.tournamentParticipants = new ArrayList<>();
    }



    public void setupQueue() {
        tournamentParticipants.forEach(player -> {
            playerQueue.enqueue(player);
        });
    }

    public TournamentPlayer getActiveTournamentPlayer(UUID uuid) {
        for (TournamentPlayer tournamentPlayer : tournamentParticipants) {
            if (tournamentPlayer.getPlayer().getUniqueId() == uuid) {
                return tournamentPlayer;
            }
        }
        return null;
    }

    public TournamentPlayer getTournamentPlayer(UUID uuid) {
        if (!tournamentPlayers.containsKey(uuid)) {
            TournamentPlayer tournamentPlayer = new TournamentPlayer(uuid);
            tournamentPlayers.put(uuid, tournamentPlayer);
        }
        return tournamentPlayers.get(uuid);
    }



    public void putNextPlayersInArenas() {

        Queue<TournamentPlayer> playersInTournament = getPlayerQueue();

        this.astroTournamentsPlugin.getArenaRegister().getArenas().values().forEach(arena -> {

            if(astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().size() == 1) {
                Player winner = astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().get(0).getPlayer();
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(Utils.fixColor("&6&lAstro&f&lPvP &6&lTournaments"));
                Bukkit.broadcastMessage(Utils.fixColor(" &f&l* &fTournament Winner: &e" + winner.getName()));
                Bukkit.broadcastMessage("");
                winner.getInventory().addItem(new ItemBuilder(Material.CHEST, "WINNER LOOT", "gg on winning", "the tourney")
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 10).colorize().build());
                astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().clear();
                astroTournamentsPlugin.getTournamentManager().getPlayerQueue().clear();
                astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().clear();
                astroTournamentsPlugin.getTournamentManager().setJoinable(false);
                astroTournamentsPlugin.getTournamentManager().setActive(false);
                winner.teleport(Bukkit.getWorld("world").getSpawnLocation());
                return;
            }

            if(!arena.isInUse() && playersInTournament.size() >= 2) {

                TournamentPlayer player1 = playersInTournament.dequeue();
                TournamentPlayer player2 = playersInTournament.dequeue();

                setupPlayer(player1, arena.getLocations().getLeft());
                setupPlayer(player2, arena.getLocations().getRight());

                arena.setCurrentlyFighting(new Pair<>(player1, player2));
                arena.setInUse(true);

                this.astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().add(new TournamentMatch(player1, player2, arena));
            }
        });

    }

    private void setupPlayer(TournamentPlayer tournamentPlayer, Location location) {
        Player player = tournamentPlayer.getPlayer();
        player.teleport(location);
        player.setHealth(20);
        giveItems(tournamentPlayer);
        Bukkit.getScheduler().runTaskLater(astroTournamentsPlugin, ()-> {
            Blessed.blessPlayer(tournamentPlayer.getPlayer());
        }, 40L);
    }

    private void giveItems(TournamentPlayer tournamentPlayer) {
        Player player = tournamentPlayer.getPlayer();

        player.getInventory().setBoots(tournamentPlayer.getBoots());
        player.getInventory().setChestplate(tournamentPlayer.getChestplate());
        player.getInventory().setLeggings(tournamentPlayer.getLeggings());
        player.getInventory().setHelmet(tournamentPlayer.getHelmet());
        player.getInventory().setItem(0, tournamentPlayer.getSword());
        player.getInventory().setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        Potion splash = new Potion(PotionType.INSTANT_HEAL, 2);
        splash.setSplash(true);
        while(Utils.hasAvaliableSlot(player, 1)) {
            player.getInventory().addItem(splash.toItemStack(1));
        }
    }





}
