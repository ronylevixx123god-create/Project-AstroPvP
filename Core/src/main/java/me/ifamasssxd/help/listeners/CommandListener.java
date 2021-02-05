/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.StringUtils
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package me.ifamasssxd.help.listeners;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;

import me.ifamasssxd.help.gui.HCFClaimingGUI;
import me.ifamasssxd.help.gui.HCFClassGui;
import me.ifamasssxd.help.json.JSONMessage;
import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerPreProcess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().contains(" ") ? event.getMessage().split(" ")[0].toLowerCase() : event.getMessage().toLowerCase();
        Player player = event.getPlayer();
        if (command.equals("/help") || command.equals("/ehelp") || command.equals("/guide") || command.equals("/?") || command.equals("/tutorial")) {
            int page = 1;
            if (event.getMessage().contains(" ")) {
                Integer given;
                String[] args = event.getMessage().split(" ");
                String pageNumber = args[1];
                if (pageNumber.equalsIgnoreCase("essentials")) {
                    return;
                }
                if (pageNumber.equalsIgnoreCase("enchants") || pageNumber.equalsIgnoreCase("enchant") || pageNumber.equalsIgnoreCase("enchantment") || pageNumber.equalsIgnoreCase("enchantments")) {
                    event.setMessage(event.getMessage().replace(command + " " + pageNumber, "/helpenchants"));
                    return;
                }
                if (pageNumber.equalsIgnoreCase("masks") || pageNumber.equalsIgnoreCase("mask")) {
                    event.setMessage("/cmask help");
                    return;
                }
                if (pageNumber.equalsIgnoreCase("pet") || pageNumber.equalsIgnoreCase("pets")) {
                    event.setMessage("/pet help");
                    return;
                }
                if (pageNumber.equalsIgnoreCase("itemlinking")) {
                    player.sendMessage(" " + CC.LightPurpleB + "Item Linking");
                    player.sendMessage("  " + CC.Aqua + "Ranked players may link an item they are holding with " + CC.LightPurple + "[item]" + CC.Aqua + ", or their entire inventory with " + CC.LightPurple + "[brag]" + CC.Aqua + "!");
                    event.setCancelled(true);
                    return;
                }
                if (pageNumber.equalsIgnoreCase("lootbox")) {
                    ArrayList msg = Lists.newArrayList((Object[])new String[]{"", "&b&lAstro Lootbox", CC.Gray + "Astro Lootboxes are special items that are released on a weekly basis. They are displayed by the " + CC.Aqua + "Lootbox Merchant at /spawn" + CC.Gray + " or in the " + CC.Aqua + "/lootbox" + CC.Gray + " menu.", "", CC.Gray + "Lootboxes are " + CC.Aqua + "only available for a specific amount of time " + CC.Gray + "before they sell out, expire, and are gone forever.", "", CC.Gray + "Lootboxes contain " + CC.Aqua + "random combination" + CC.Gray + " of extremely rare, typically otherwise unobtainable items. Don't miss your chance, collect every " + CC.Aqua + "/lootbox" + CC.Gray + " you can!", ""});
                    msg.forEach(pl -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', (String)pl)));
                    event.setCancelled(true);
                    return;
                }
                if (pageNumber.equalsIgnoreCase("end") && CosmicUtils.get().isArkkitEnabled()) {
                    ArrayList msg = Lists.newArrayList((Object[])new String[]{"", "&d&lThe Ender Dimension", "&7The End is a dangerous, hardcore, PvP-enabled realm that you &c&ncannot&7 teleport directly out of; in order to escape, you must find the &cExit Portal.", "", "&7The End disables all &nSoul, Heroic, and Mastery enchantments.&7 In addition, Knockback enchantments are decreased and command execution is limited.", "", "&7Custom Creepers and Ender Monsters naturally spawn throughout the End. The Custom Creepers drop custom eggs, and the Ender Monsters drop amplified XP, Money, and /gkit Items. The more you kill, the better the loot.", "", "&7The &4&lTimeless Dragon&7 boss spawns in the center pit of The End every &n7-12 hours.&7 One of the strongest enemies in the game, with some of the best loot, including pieces of the infamous \"&e&lDragon Slayer&7\" armor set.", "", "&7The End can be accessed through the /warp menu or by executing &d&n/warp end", ""});
                    msg.forEach(pl -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', (String)pl)));
                    event.setCancelled(true);
                    return;
                }
                if (pageNumber.equalsIgnoreCase("quests")) {
                    player.sendMessage("");
                    player.sendMessage(CC.YellowB + CC.Underline + "Player Quests");
                    player.sendMessage("");
                    player.sendMessage(CC.Gray + "Complete Player " + CC.Yellow + "/quests" + CC.Gray + " every day to earn " + CC.GoldU + "Quest Tokens" + CC.Gray + ", earn more Quest Tokens for a quest depending on its difficulty.");
                    player.sendMessage(CC.GoldB + "Difficulties: " + CC.Green + "Easy (1)" + CC.Gray + ", " + CC.Yellow + "Medium (2)" + CC.Gray + ", " + CC.Red + "Hard (3)");
                    player.sendMessage("");
                    player.sendMessage(CC.Gray + "Quest Tokens can be spent at the " + CC.Yellow + "/questmaster" + CC.Gray + " and " + CC.Yellow + "Mystery Merchant" + CC.Gray + " to unlock epic rewards and treasure!");
                    player.sendMessage("");
                    player.sendMessage(CC.Gray + "The " + CC.Yellow + "Mystery Merchant" + CC.Gray + " arrives weekly on Saturday. " + CC.YellowU + "12PM" + CC.Yellow + " CST");
                    player.sendMessage(CC.Gray + "A new " + CC.Yellow + "mystery item" + CC.Gray + " will be available for purchase for " + CC.Yellow + "24h" + CC.Gray + " after his arrival every week!");
                    player.sendMessage("");
                    event.setCancelled(true);
                    return;
                }
                if ((AstroCore.getInstance().getHelpCommand().isHardCoreFactions() || AstroCore.getInstance().getHelpCommand().isClassesEnabled()) && pageNumber.equalsIgnoreCase("classes")) {
                    new HCFClassGui(event.getPlayer()).openGUI(AstroCore.getInstance());
                    event.setCancelled(true);
                    event.setMessage("/null");
                    return;
                }
                if (AstroCore.getInstance().getHelpCommand().isHcfOutpostsEnabled() && (pageNumber.equalsIgnoreCase("stronghold") || pageNumber.equalsIgnoreCase("strongholds"))) {
                    event.setMessage("/stronghold menu");
                    return;
                }
                if (AstroCore.getInstance().getHelpCommand().isHcfCrateEnabled() && (pageNumber.equalsIgnoreCase("crate") || pageNumber.equalsIgnoreCase("crates"))) {
                    event.setMessage("/crate help");
                    return;
                }
                if (AstroCore.getInstance().getHelpCommand().isHardCoreFactions()) {
                    if (pageNumber.equalsIgnoreCase("claiming")) {
                        new HCFClaimingGUI(event.getPlayer()).openGUI(AstroCore.getInstance());
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("nether")) {
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.RedB + "The Nether"));
                        player.sendMessage(CC.Gray + "The nether is a dangerous place full of resources that can be used to give your faction an edge over the competition!");
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.RedB + "Resources Available"));
                        JSONMessage.createRunMessage(StringUtils.getCenteredMessage(CC.RedB + " * " + CC.Yellow + "Glowstone Mountain"), ChatColor.YELLOW, "/glowstonemountain", CC.Yellow + "/glowstonemountain\n\n" + CC.Green + "Click for more info!").sendToPlayer(player);
                        JSONMessage.createRunMessage(StringUtils.getCenteredMessage(CC.RedB + " * " + CC.Red + "Nether KOTH"), ChatColor.RED, "/koth nether", CC.Yellow + "/koth nether\n\n" + CC.Green + "Click for more info!").sendToPlayer(player);
                        player.sendMessage(StringUtils.getCenteredMessage(CC.RedB + " * " + CC.Red + "Nether Fortresses"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.RedB + " * " + CC.Aqua + "Ghast Tears"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.RedB + " * " + CC.Gold + "Magma Cream"));
                        player.sendMessage("");
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("end")) {
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + "The End"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.Gray + "The end is a dark, gloomy land where dangerous creatures can be found and slain for their drops!"));
                        this.sendCentered(player, CC.Gray + "Enter the End Portals at " + CC.LightPurple + "+/-1500x" + CC.Gray + ", " + CC.LightPurple + "+/-1500z!");
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + "Resources Available"));
                        JSONMessage.createRunMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + " * " + CC.LightPurple + "End KOTH"), ChatColor.LIGHT_PURPLE, "/koth end", CC.Yellow + "/koth end\n\n" + CC.Green + "Click for more info!").sendToPlayer(player);
                        player.sendMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + " * " + CC.Yellow + "Endermen"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + " * " + CC.Green + "Creepers"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + " * " + CC.Green + "Slimes"));
                        player.sendMessage("");
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("treasuredrops")) {
                        player.sendMessage("");
                        this.sendCentered(player, CC.AquaB + "Treasure Drops");
                        player.sendMessage(CC.Gray + "A chest filled with treasure will land in a random " + CC.RedB + "WarZone" + CC.Gray + " location once every few hours, no " + CC.GrayU + "PvP Timers" + CC.Gray + " allowed!");
                        String bul = CC.AquaB + " * " + CC.White;
                        player.sendMessage("");
                        this.sendCentered(player, CC.AquaB + "Loot Obtainable From Chest");
                        this.sendCentered(player, bul + "Ranks");
                        this.sendCentered(player, bul + "Trophies");
                        this.sendCentered(player, bul + "Enchantment Books");
                        this.sendCentered(player, bul + "Full Armor Sets");
                        this.sendCentered(player, bul + "Powerful Weapons");
                        this.sendCentered(player, bul + "Crate Keys");
                        this.sendCentered(player, bul + "Lives");
                        this.sendCentered(player, bul + "Titles");
                        this.sendCentered(player, bul + "Kill Trackers");
                        player.sendMessage("");
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("stronghold")) {
                        player.sendMessage("");
                        this.sendCentered(player, CC.RedB + "Stronghold KOTH");
                        this.sendCentered(player, CC.Gray + "Stronghold is available every " + CC.LightPurple + "Saturday" + CC.Gray + " at " + CC.LightPurple + "1PM CST" + CC.Gray + ".");
                        this.sendCentered(player, CC.Gray + "** The faction to last capture the Stronghold will be able to access the " + CC.LightPurpleU + "16" + CC.LightPurple + " Stronghold Chests" + CC.Gray + " that respawn every 3 hours inside the Stronghold area!");
                        player.sendMessage("");
                        this.sendCentered(player, CC.LightPurple + "Stronghold Chests " + CC.Gray + "contain loot similar to the " + CC.LightPurpleB + "KOTH" + CC.Gray + " Crate!");
                        player.sendMessage("");
                        event.setMessage("/koth stronghold");
                        return;
                    }
                    if (pageNumber.equals("trophies")) {
                        String bul = CC.AquaB + " * " + CC.White;
                        player.sendMessage("");
                        this.sendCentered(player, CC.AquaB + "Trophies and Faction Points");
                        player.sendMessage("");
                        this.sendCentered(player, CC.Gray + "** " + CC.LightPurple + "Trophies" + CC.Gray + " generate an increasing amount of " + CC.LightPurple + "Faction Points" + CC.Gray + ", capping potential points per hour after 7 days. " + CC.LightPurple + "Trophies" + CC.Gray + " can be obtained from random world events, each trophy must be placed inside your faction territory.");
                        player.sendMessage(CC.Gray + "The faction at " + CC.GreenB + "#1" + CC.Gray + " in '" + CC.Aqua + "/trophy top" + CC.Gray + "' will " + CC.Green + "WIN" + CC.Gray + " this season!");
                        player.sendMessage("");
                        this.sendCentered(player, CC.Aqua + "Trophies can be obtained from the following events");
                        this.sendCentered(player, bul + "KOTH");
                        this.sendCentered(player, bul + "Nether KOTH");
                        this.sendCentered(player, bul + "End KOTH");
                        this.sendCentered(player, bul + "Outposts");
                        this.sendCentered(player, bul + "Treasure Drops");
                        this.sendCentered(player, bul + "Stronghold");
                        this.sendCentered(player, bul + "EOTW Events");
                        this.sendCentered(player, CC.Gray + "");
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("crates")) {
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.AquaB + "HCF Crates"));
                        JSONMessage message = new JSONMessage("");
                        message.addText(CC.Gray + "Crate Keys can be obtained from falling ");
                        message.addRunCommand(CC.AquaB + "Treasure Drops", ChatColor.AQUA, "/help treasuredrops", CC.Aqua + "/help treasuredrops\n\n" + CC.Yellow + "Click to view info about Treasure Drops!");
                        message.addText(CC.Gray + ", capturing each ", ChatColor.GRAY);
                        message.addRunCommand(CC.AquaB + "KOTH", ChatColor.AQUA, "/koth", CC.Aqua + "/koth\n\n" + CC.Yellow + "Click to view /koth info!");
                        message.addText(CC.Gray + " or unlocked at ", ChatColor.GRAY);
                        message.addURL(CC.AquaU + "buy.astropvp.net", ChatColor.AQUA, "https://buy.astropvp.net");
                        message.addText(CC.Gray + "!", ChatColor.GRAY);
                        message.sendToPlayer(player);
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.WhiteB + "Crates Available..."));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.BlueB + "Elite Crate"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.YellowB + "Ultimate Crate"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.GoldB + "Legendary Crate"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.RedB + "Kill Message Crate"));
                        player.sendMessage(StringUtils.getCenteredMessage(CC.Aqua + CC.Magic + "|" + CC.Aqua + " " + String.format("%sK%s.%sO%s.%sT%s.%sH" + CC.Aqua + " " + CC.Magic + "|" + CC.GreenB + " Crate", CC.RedB, CC.GoldB, CC.YellowB, CC.GreenB, CC.AquaB, CC.BlueB, CC.LightPurpleB, CC.DarkPurpleB)));
                        player.sendMessage("");
                        player.sendMessage(StringUtils.getCenteredMessage(CC.Gray + "Visit the spawn to view all potential crate prizes!"));
                        player.sendMessage("");
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("discord")) {
                        player.sendMessage("");
                        this.sendCentered(player, CC.LightPurpleB + "Astro Discord");
                        this.sendCentered(player, CC.WhiteU + "discord.astropvp.net");
                        player.sendMessage("");
                        event.setCancelled(true);
                        event.setMessage("/null");
                        return;
                    }
                    if (pageNumber.equalsIgnoreCase("hcf")) {
                        event.setMessage("/help hcf");
                        return;
                    }
                }
                if ((given = NumberUtils.parseIntOrNull(pageNumber)) != null) {
                    page = given;
                }
            }
            event.setCancelled(true);
            AstroCore.getInstance().getHelpCommand().onCommand(event.getPlayer(), page);
            event.setMessage("/null");
        } else if (command.equals("/client")) {
            Player pl2 = event.getPlayer();
            pl2.sendMessage("");
            pl2.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "Astro" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Client");
            pl2.sendMessage(ChatColor.WHITE + ChatColor.UNDERLINE.toString() + "https://client.astropvp.net");
            pl2.sendMessage(ChatColor.GRAY + "Click the link above to visit the forums.");
            pl2.sendMessage(ChatColor.GRAY + "Use " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/syncaccount" + ChatColor.GRAY + " to sync your forums account with your in game rank.");
            pl2.sendMessage("");
            event.setCancelled(true);
            event.setMessage("/null");
        } else if (command.equals("/buylink") || command.equals("/buy")) {
            Player pl3 = event.getPlayer();
            pl3.sendMessage("");
            pl3.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "Astro" + ChatColor.YELLOW + ChatColor.BOLD.toString() + "PvP Store");
            pl3.sendMessage(ChatColor.WHITE + ChatColor.UNDERLINE.toString() + "http://buy.astropvp.net");
            pl3.sendMessage(ChatColor.GRAY + "Click the link above to visit the shop.");
            pl3.sendMessage("");
            event.setCancelled(true);
            event.setMessage("/null");
        } else if (command.equals("/enchants") || command.equals("/enchantments")) {
            Player pl4 = event.getPlayer();
            event.setCancelled(true);
            pl4.chat("/help enchants");
        }
    }

    private void sendCentered(Player player, String msg) {
        player.sendMessage(StringUtils.getCenteredMessage(msg));
    }
}

