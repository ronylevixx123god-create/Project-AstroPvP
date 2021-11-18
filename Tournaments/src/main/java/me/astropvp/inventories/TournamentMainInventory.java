package me.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.struct.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class TournamentMainInventory {
     public static void openInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, "Tournaments");
        inv.setItem(0, getStatsItem(player));
        inv.setItem(8, getInfoItem());
        inv.setItem(26, getProfilesItem());
        inv.setItem(18, getShopItem(player));
        player.openInventory(inv);
     }

     private static ItemStack getStatsItem(Player player) {
         PlayerData data = CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player);
         ItemStack item = new ItemStack(Material.SKULL_ITEM, 1 , (short)3);
         SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
         skullMeta.setOwner(player.getName());
         skullMeta.setDisplayName(CC.GoldB + player.getName() + "'s Tournament Stats");
         List<String> lore = new ArrayList<>();
         lore.add("");
         lore.add(CC.YellowB + "ELO: " + CC.White + data.getElo());
         lore.add(CC.YellowB + "*" + CC.Gray + " Position:");
         lore.add("");
         lore.add(CC.YellowB + "Tournament W/L Ratio: " + CC.White + data.getTwl());
         lore.add(CC.YellowB + "*" + CC.Gray + " Tournament Wins: " + CC.White + data.getTournamentwins());
         lore.add(CC.YellowB + "*" + CC.Gray + " Tournament Losses: " + CC.White + data.getTournamentlosses());
         lore.add("");
         lore.add(CC.YellowB + "Match W/L Ratio: " + CC.White + data.getMwl());
         lore.add(CC.YellowB + "*" + CC.Gray + " Match Wins: " + CC.White + data.getMatchwins());
         lore.add(CC.YellowB + "*" + CC.Gray + " Match Losses: " + CC.White + data.getMatchlosses());
         lore.add("");
         lore.add(CC.YellowB + "K/D Ratio: " + data.getKd());
         lore.add(CC.YellowB + "* " + CC.Gray + "Kills: " + CC.White + data.getKills());
         lore.add(CC.YellowB + "* " + CC.Gray + "Deaths: " + CC.White + data.getDeaths());
         lore.add("");
         lore.add(CC.Gray + "Click to view leaderboards.");
         skullMeta.setLore(lore);
         item.setItemMeta(skullMeta);
         return item;
     }

     private static ItemStack getInfoItem() {
         ItemStack item = new ItemStack(Material.BOOK);
         ItemMeta meta = item.getItemMeta();
         meta.setDisplayName(CC.translate("&a&lAstro Tournaments"));
         List<String> lore = new ArrayList<>();
         lore.add(CC.Gray + "The ultimate battle of all battles, tournaments are a");
         lore.add(CC.White + "round-based, knockout event " + CC.Gray + "ensuring only the best");
         lore.add(CC.Gray + "and the smartest survive while the rest are left to watch");
         lore.add(CC.Gray + "in awe - are you that gladiator?");
         lore.add("");
         lore.add(CC.Gray + "Tournaments are " + CC.White + "made up of round " + CC.Gray + "where each player");
         lore.add(CC.Gray + "must win their match in the round to progress to the");
         lore.add(CC.Gray + "next. If a player loses their match, they are eliminated");
         lore.add(CC.Gray + "from the tournament. The objective is simple, defeat");
         lore.add(CC.Gray + "all of your foes and show no mercy!");
         lore.add("");
         lore.add(CC.Gray + "The winner of each tournament will not be permitted to");
         lore.add(CC.Gray + "participate in any following tournaments for the rest");
         lore.add(CC.Gray + "of the week but with the " + CC.White + "following items and perks");
         lore.add(CC.White + "unlocked until next week's tournament, " + CC.Gray + "why would");
         lore.add(CC.Gray + "they even want to?");
         lore.add("");
         lore.add(CC.AquaB + "Winner Rewards");
         lore.add(CC.AquaB + "* " + CC.White + "Challengers Crown Lootbox " + CC.Gray + "(Right-Click to preview loot)");
         lore.add(CC.AquaB + "* " + CC.White + "Weekly /lootbox or /cc");
         lore.add(CC.AquaB + "* " + CC.White + "Limited Access Title " + CC.Gray + "(until next week's tournament)");
         lore.add(CC. Aqua + " - " + CC.White + "God Mode: " + CC.DarkGray + "[" + CC.Gold + "♛" + CC.DarkGray + "]");
         lore.add(CC.Aqua + " - " + CC.White + "Team God Mode: " + CC.DarkGray + "[" + CC.Gold + "♚" + CC.DarkGray + "]");
         lore.add(CC.AquaB + "* " + CC.White + "No /ah tax " + CC.Gray + "(until next week's tournament)");
         lore.add(CC.AquaB + "* " + CC.White + "No /cf tax " + CC.Gray + "(until next week's tournament)");
         lore.add("");
         lore.add(CC.DarkAquaB + "ELO Top #1 Rewards");
         lore.add(CC.DarkAquaB + "* " + CC.White + "Limited Access Title " + CC.DarkGray + "[" + CC.Gold + "⤁⚔⬼" + CC.DarkGray + "]");
         lore.add(CC.DarkAquaB + "* " + CC.White + "Upside-down Baby Undead Horse /disguise");
         lore.add(CC.DarkAquaB + "* " + CC.White + "Salty CosmicClient Emote");
         meta.setLore(lore);
         item.setItemMeta(meta);
         return item;
     }

     private static ItemStack getProfilesItem() {
         ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
         ItemMeta meta = item.getItemMeta();
         meta.setDisplayName(CC.GoldB + "Profiles");
         List<String> lore = new ArrayList<>();
         lore.add(CC.Gray + "Customize your inventory layout and");
         lore.add(CC.Gray + "select your custom masks, item skins");
         lore.add(CC.Gray + "and inventory pets.");
         lore.add("");
         lore.add(CC.RedB + "NOTE: " + CC.Red + "Every week, a random selection");
         lore.add(CC.Red + "of masks, item skins and inventory pets");
         lore.add(CC.Red + "are restricted for use.");
         lore.add("");
         lore.add(CC.Gray + "Click to view profiles.");
         meta.setLore(lore);
         item.setItemMeta(meta);
         return item;
     }

     private static ItemStack getShopItem(Player player) {
         PlayerData data = CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player);
         ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.YellowB + "Tournament ELO Point Shop");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Yellow + "Tournament Points: " + CC.White + data.getPoints());
        lore.add(CC.Gray + " (max: 5,000 points)");
        lore.add(CC.Yellow + "Point Shop Status: " + CC.RedB + "CLOSED");
        lore.add(CC.Yellow + "Next Restock: " + CC.White + "1d");
        lore.add(CC.Gray + " (Sunday @ 12am CST)");
        lore.add("");
        lore.add(CC.Gray + "ELO points are awarded through " + CC.White + "participation");
        lore.add(CC.White + "in tournaments, " + CC.Gray + "the better you do, the further");
        lore.add(CC.Gray + "along you go, the more points you stand to gain!");
        lore.add("");
        lore.add(CC.Gray + "Each tournament match has a hologram with the");
        lore.add(CC.Gray + "amount of points you will gain if you win. Using");
        lore.add(CC.Gray + "ELO Points in the Point Shop will not affect your");
        lore.add(CC.Gray + "ELO rating nor your standings in /tournaments");
        lore.add("");
        lore.add(CC.Gray + "Filled with a random selection of items and only");
        lore.add(CC.Gray + "available for " + CC.White + "24 hours" + CC.Gray + ", spent your points on any");
        lore.add(CC.Gray + "item in this shop!");
        lore.add("");
        lore.add(CC.Gray + "Click to open the point shop.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
     }
}
