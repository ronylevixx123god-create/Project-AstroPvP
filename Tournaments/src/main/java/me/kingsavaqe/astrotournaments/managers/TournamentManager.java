package me.kingsavaqe.astrotournaments.managers;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.Getter;
import lombok.Setter;
import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import me.kingsavaqe.astrotournaments.objects.TournamentMatch;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.*;
import me.kingsavaqe.astrotournaments.utils.Queue;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.heroicArmor.HeroicArmor;
import org.arkhamnetwork.enchantments.EnchantmentBook;
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

public class TournamentManager {

    private final AstroTournamentsPlugin plugin;
    public TournamentManager(AstroTournamentsPlugin plugin) {
        this.plugin = plugin;
    }

    @Getter @Setter
    private boolean active;

    @Getter @Setter
    private boolean joinable;

    @Getter @Setter
    public Queue<TournamentPlayer> playerQueue;

    @Getter
    private final List<TournamentPlayer> tournamentParticipants = new ArrayList<>();

    @Getter
    private final List<TournamentMatch> tournamentMatchList = new ArrayList<>();

    @Getter
    private final Map<UUID, TournamentPlayer> tournamentPlayers = new HashMap<>();

    @Getter @Setter
    private Location waitingRoomLocation;

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
            TournamentPlayer tournamentPlayer = new TournamentPlayer(uuid, null, null, null, null, null);
            tournamentPlayers.put(uuid, tournamentPlayer);

        }
        return tournamentPlayers.get(uuid);
    }



    public void putNextPlayersInArenas() {

        Queue<TournamentPlayer> playersInTournament = getPlayerQueue();

        this.plugin.getArenaRegister().getArenas().values().forEach(arena -> {

            if(plugin.getTournamentManager().getTournamentParticipants().size() == 1) {
                Player winner = plugin.getTournamentManager().getTournamentParticipants().get(0).getPlayer();
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(Utils.fixColor("&6&lAstro&f&lPvP &6&lTournaments"));
                Bukkit.broadcastMessage(Utils.fixColor(" &f&l* &fTournament Winner: &e" + winner.getName()));
                Bukkit.broadcastMessage("");
                winner.getInventory().addItem(new ItemBuilder(Material.CHEST, "WINNER LOOT", "gg on winning", "the tourney")
                        .addUnsafeEnchantment(Enchantment.DURABILITY, 10).colorize().build());
                plugin.getTournamentManager().getTournamentMatchList().clear();
                plugin.getTournamentManager().getPlayerQueue().clear();
                plugin.getTournamentManager().getTournamentParticipants().clear();
                plugin.getTournamentManager().setJoinable(false);
                plugin.getTournamentManager().setActive(false);
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

                this.plugin.getTournamentManager().getTournamentMatchList().add(new TournamentMatch(player1, player2, arena));
            }
        });

    }

    private void setupPlayer(TournamentPlayer tournamentPlayer, Location location) {
        Player player = tournamentPlayer.getPlayer();
        player.teleport(location);
        player.setMaxHealth(20);
        player.setHealth(20);
        giveLoot(tournamentPlayer);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
    }

    private void giveLoot(TournamentPlayer tournamentPlayer) {
        Player player = tournamentPlayer.getPlayer();

        player.getInventory().setBoots();
        player.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS, "&6Tournament Armor").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());
        player.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE, "&6Tournament Armor").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());
        player.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET, "&6Tournament Armor").addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).setUnbreakable(true).build());
        player.getInventory().setItem(0, new ItemBuilder(Material.DIAMOND_SWORD, "&6Tournament Weapon").addEnchantment(Enchantment.DAMAGE_ALL, 3).build());
        player.getInventory().setItem(1, new ItemStack(Material.ENDER_PEARL, 16));
        Potion splash = new Potion(PotionType.INSTANT_HEAL, 2);
        splash.setSplash(true);
        while(Utils.hasAvaliableSlot(player, 1)) {
            player.getInventory().addItem(splash.toItemStack(1));
        }
    }

    private ItemStack getHelmet() {

        return EnchantmentBook.applyTransmogScroll( ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6Tournament Armor")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .setUnbreakable(true)
                .build(), HeroicArmor.HELMET), getHelmetEnchants()));
    }

    private ItemStack getChestplate() {
        return EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6Tournament Armor")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .build(), HeroicArmor.CHESTPLATE), getChestplateEnchants()));
    }

    private ItemStack getLeggings() {
        return EnchantmentBook.applyTransmogScroll( ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6Tournament Armor")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .build(), HeroicArmor.LEGGINGS), getLeggingsEnchants()));
    }

    private ItemStack getBoots() {
        return EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6Tournament Armor")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .build(), HeroicArmor.BOOTS), getBootsEnchants()));
    }

    private ItemStack getSword() {
        return EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6Tournament Armor")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .build(), HeroicArmor.SWORD), getSwordEnchants()));
    }

    public LinkedList<CustomEnchantment> getHelmetEnchants() {
        return  new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Immortal"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Protection"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Poisoned"), EnchantmentAPI.getEnchantment("Frozen"), EnchantmentAPI.getEnchantment("Stormcaller"), EnchantmentAPI.getEnchantment("Smoke Bomb"), EnchantmentAPI.getEnchantment("Ender Shift")));
    }

    public LinkedList<CustomEnchantment> getChestplateEnchants() {
        return  new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Holy Aegis"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Heroic Enchant Reflect"), EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Vengeful Diminish"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Leadership"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Self Destruct")));
    }

    public LinkedList<CustomEnchantment> getLeggingsEnchants() {
        return  new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Mighty Cactus"), EnchantmentAPI.getEnchantment("Holy Aegis"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Blood Link"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Wither"), EnchantmentAPI.getEnchantment("Plague Carrier")));
    }

    public LinkedList<CustomEnchantment> getBootsEnchants() {
        return  new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Guided Rocket Escape"), EnchantmentAPI.getEnchantment("Polymorphic Metaphysical"), EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Phoenix"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Undead Ruse"), EnchantmentAPI.getEnchantment("Repair Guard"), EnchantmentAPI.getEnchantment("Frozen"), EnchantmentAPI.getEnchantment("Planetary Deathbringer")));
    }

    public LinkedList<CustomEnchantment> getSwordEnchants() {
        return  new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Neutralize"), EnchantmentAPI.getEnchantment("Blackout"), EnchantmentAPI.getEnchantment("Permanent Execute"), EnchantmentAPI.getEnchantment("Perfect Solitude"), EnchantmentAPI.getEnchantment("Reality Inversion"), EnchantmentAPI.getEnchantment("Unrestrained Enrage"), EnchantmentAPI.getEnchantment("Reflective Block"), EnchantmentAPI.getEnchantment("Deep Bleed"), EnchantmentAPI.getEnchantment("Bloody Depp Wounds"), EnchantmentAPI.getEnchantment("Shadow Assassin"), EnchantmentAPI.getEnchantment("Divine Immolation"), EnchantmentAPI.getEnchantment("Rage"), EnchantmentAPI.getEnchantment("Silence"), EnchantmentAPI.getEnchantment("Double Strike"),  EnchantmentAPI.getEnchantment("Devour")));
    }



}
