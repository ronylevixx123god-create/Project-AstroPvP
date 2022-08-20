package me.kingsavaqe.astroenvoy.commands;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.envoy.Envoy;
import me.kingsavaqe.astroenvoy.envoy.EnvoyManager;
import me.kingsavaqe.astroenvoy.envoy.LootItem;
import me.kingsavaqe.astroenvoy.file.FileManager;
import me.kingsavaqe.astroenvoy.menus.EnvoyMenu;
import me.kingsavaqe.astroenvoy.playerdata.PlayerData;
import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import me.kingsavaqe.astroenvoy.stats.Stats;
import me.kingsavaqe.astroenvoy.stats.StatsManager;
import me.kingsavaqe.astroenvoy.utils.NBTUtil;
import me.kingsavaqe.astroenvoy.utils.NumberUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnvoyCommand implements CommandExecutor, TabCompleter {
    private final AstroEnvoyPlugin instance;

    public EnvoyCommand(AstroEnvoyPlugin instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        FileManager fileManager = this.instance.getFileManager();
        FileConfiguration configLoad = fileManager.getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        ItemStack is = null;
        if (!(sender instanceof Player)) {
            if (args.length >= 2 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("givesummoner")) {
                if (args.length != 4) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.GiveSummoner.Invalid.Message")));
                    return true;
                }
                Player targetPlayer = Bukkit.getServer().getPlayer(args[2]);
                if (targetPlayer == null) {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Commands.Envoy.Admin.GiveSummoner.Connected.Message"))
                                    .replace("%player", args[2]));
                    return true;
                }
                if (!args[3].matches("[0-9]+")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.GiveSummoner.Numerical.Message")));
                    return true;
                }
                int itemAmount = Integer.parseInt(args[3]);
                if (configLoad.getString("Envoy.Summoner.Item.Data") == null) {
                    is = new ItemStack(Material.valueOf(configLoad.getString("Envoy.Summoner.Item.Material")), itemAmount);
                } else {
                    is = new ItemStack(Material.valueOf(configLoad.getString("Envoy.Summoner.Item.Material")), itemAmount, (short) configLoad.getInt("Envoy.Summoner.Item.Data"));
                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Envoy.Summoner.Displayname")));
                if (configLoad.getString("Envoy.Summoner.Lore") != null) {
                    List<String> itemLore = new ArrayList<>();
                    for (String itemLoreLine : configLoad.getStringList("Envoy.Summoner.Lore"))
                        itemLore.add(ChatColor.translateAlternateColorCodes('&', itemLoreLine));
                    im.setLore(itemLore);
                }
                is.setItemMeta(im);
                is = NBTUtil.addTag(is, "Envoy", "Envoy");
                if (configLoad.getBoolean("Envoy.Summoner.Full.Drop")) {
                    if (targetPlayer.getInventory().firstEmpty() == -1) {
                        targetPlayer.getWorld().dropItemNaturally(targetPlayer.getLocation(), is);
                    } else {
                        targetPlayer.getInventory().addItem(is);
                    }
                } else {
                    if (targetPlayer.getInventory().firstEmpty() == -1) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Commands.Envoy.Admin.GiveSummoner.Full.Other.Message")
                                .replace("%player", targetPlayer.getName())));
                        return true;
                    }
                    targetPlayer.getInventory().addItem(is);
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Admin.GiveSummoner.Given.Other.Message")
                        .replace("%player", targetPlayer.getName()).replace("%amount", String.valueOf(itemAmount))));
                return true;
            }
            sender.sendMessage(
                    this.instance.getDescription().getName() + " | Error: You must be a player to perform that command.");
            return true;
        }
        Player player = (Player) sender;
        PlayerDataManager playerDataManager = this.instance.getPlayerDataManager();
        EnvoyManager envoyManager = this.instance.getEnvoyManager();
        StatsManager statsManager = this.instance.getStatsManager();
        if (args.length == 0) {
            EnvoyMenu.getInstance().open(player);
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
        } else if (args[0].equalsIgnoreCase("help")) {
            for (String helpLine : configLoad.getStringList("Commands.Envoy.Help.Lines"))
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpLine));
            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
        } else if (args[0].equalsIgnoreCase("notifications")) {
            PlayerData playerData = playerDataManager.getPlayerData(player);
            if (playerData.hasNotifications()) {
                playerData.setNotifications(false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Notifications.Disabled.Message")));
            } else {
                playerData.setNotifications(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Notifications.Enabled.Message")));
            }
            player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1.0F, 1.0F);
        } else if (args[0].equalsIgnoreCase("top")) {
            if (args.length > 2) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Top.Invalid.Message")));
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                return true;
            }
            int pageNumber = 1;
            if (args.length == 2)
                if (args[1].matches("[0-9]+")) {
                    if (args[1].length() >= 10) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Commands.Envoy.Top.Pages.Message")));
                        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                        return true;
                    }
                    pageNumber = Integer.parseInt(args[1]);
                    if (pageNumber == 0)
                        pageNumber = 1;
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Top.Numerical.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
            int index = pageNumber * 8 - 8;
            int endIndex = (index >= statsManager.getStats().size()) ? (statsManager.getStats().size() - 1) : (index + 8);
            int nextEndIndex = (int) Math.round((statsManager.getStats().size() / 8) + 0.5D);
            if (pageNumber > nextEndIndex) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Top.Pages.Message")));
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                return true;
            }
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', configLoad.getString("Commands.Envoy.Top.Title.Message")
                            .replace("%current_page", String.valueOf(pageNumber)).replace("%max_page", String.valueOf(nextEndIndex))));
            for (; index < endIndex; index++) {
                if (statsManager.getStats().size() > index) {
                    Stats stats = statsManager.getStats().get(index);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Top.Player.Message")
                            .replace("%position", String.valueOf(index + 1)).replace("%player", stats.getName())
                            .replace("%opened", NumberUtil.formatNumberByCommas(stats.getOpened()))));
                }
            }
            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
        } else if (args[0].equalsIgnoreCase("stats")) {
            String targetPlayerName;
            if (args.length > 2) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Stats.Invalid.Message")));
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                return true;
            }
            int targetPlayerCaptures = 0;
            if (args.length == 2) {
                Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
                if (targetPlayer == null) {
                    Stats stats = statsManager.getStats(args[1]);
                    if (stats == null) {
                        player.sendMessage(
                                ChatColor.translateAlternateColorCodes('&', configLoad
                                        .getString("Commands.Envoy.Stats.Fetch.Message"))
                                        .replace("%player", args[1]));
                        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                        return true;
                    }
                    targetPlayerName = stats.getName();
                    targetPlayerCaptures = stats.getOpened();
                } else {
                    targetPlayerName = targetPlayer.getName();
                    targetPlayerCaptures = playerDataManager.getPlayerData(targetPlayer).getOpened();
                }
            } else {
                targetPlayerName = player.getName();
                targetPlayerCaptures = playerDataManager.getPlayerData(player).getOpened();
            }
            if (targetPlayerName.equalsIgnoreCase(player.getName())) {
                for (String statsLine : configLoad.getStringList("Commands.Envoy.Stats.Stats.Yourself.Lines"))
                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', statsLine.replace("%player", player.getName())
                                    .replace("%opened", NumberUtil.formatNumberByCommas(targetPlayerCaptures))));
            } else {
                for (String statsLine : configLoad.getStringList("Commands.Envoy.Stats.Stats.Target.Lines"))
                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', statsLine.replace("%player", targetPlayerName)
                                    .replace("%opened", NumberUtil.formatNumberByCommas(targetPlayerCaptures))));
            }
            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
        } else if (args[0].equalsIgnoreCase("admin")) {
            if (!player.hasPermission("jurassicenvoy.admin") && !player.hasPermission("jurassicenvoy.*")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Admin.Permission.Message")));
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                return true;
            }
            Envoy envoy = envoyManager.getEnvoy();
            if (args.length == 1 || args[1].equalsIgnoreCase("help")) {
                for (String helpLine : configLoad.getStringList("Commands.Envoy.Admin.Help.Lines"))
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpLine));
                player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("forcestart")) {
                if (envoy.hasStarted()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.ForceStart.Started.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (!envoy.hasSetup()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.ForceStart.Setup.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                envoy.setCurrentStartTime(0);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Admin.ForceStart.ForceStarted.Message")));
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("addlootitem")) {
                if (args.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddLootItem.Invalid.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (!args[2].matches("[0-9]+")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddLootItem.Numerical.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (args[2].length() > 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddLootItem.Range.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                int chance = Integer.valueOf(args[2]).intValue();
                if (chance < 1 || chance > 100) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddLootItem.Range.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddLootItem.Hand.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                envoy.addLootItem(new LootItem(player.getItemInHand().clone(), chance));
                envoyManager.saveEnvoy();
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Commands.Envoy.Admin.AddLootItem.Added.Message"))
                                .replace("%chance", String.valueOf(chance)));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("clearitems")) {
                envoy.getLootItems().clear();
                envoyManager.saveEnvoy();
                player.sendMessage(CC.Red + "Reset the loot");
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                return true;

            } else if (args[1].equalsIgnoreCase("addcrate")) {
                if (args.length != 2) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddCrate.Invalid.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (envoy.isCrateLocation(player.getLocation())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddCrate.Already.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (player.getLocation().getBlock().getType() != Material.AIR) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.AddCrate.Block.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                envoy.addCrateLocation(player.getLocation().getBlock().getLocation());
                envoyManager.saveEnvoy();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Admin.AddCrate.Added.Message")));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("setduration")) {
                if (args.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.SetDuration.Invalid.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (!args[2].matches("[0-9]+")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.SetDuration.Numerical.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                envoy.setFixedDuration(Integer.valueOf(args[2]).intValue());
                envoyManager.saveEnvoy();
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Commands.Envoy.Admin.SetDuration.Set.Message"))
                                .replace("%seconds", args[2]));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("setstarttime")) {
                if (args.length != 3) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.SetStartTime.Invalid.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (!args[2].matches("[0-9]+")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.SetStartTime.Numerical.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                envoy.setCurrentStartTime(Integer.valueOf(args[2]).intValue());
                envoy.setFixedStartTime(Integer.valueOf(args[2]).intValue());
                envoyManager.saveEnvoy();
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Commands.Envoy.Admin.SetStartTime.Set.Message"))
                                .replace("%seconds", args[2]));
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("givesummoner")) {
                if (args.length != 4) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.GiveSummoner.Invalid.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                Player targetPlayer = Bukkit.getServer().getPlayer(args[2]);
                if (targetPlayer == null) {
                    player.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Commands.Envoy.Admin.GiveSummoner.Connected.Message"))
                                    .replace("%player", args[2]));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                if (!args[3].matches("[0-9]+")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.GiveSummoner.Numerical.Message")));
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                    return true;
                }
                int itemAmount = Integer.valueOf(args[3]).intValue();
                if (configLoad.getString("Envoy.Summoner.Item.Data") == null) {
                    is = new ItemStack(Material.valueOf(configLoad.getString("Envoy.Summoner.Item.Material")), itemAmount);
                } else {
                    is = new ItemStack(Material.valueOf(configLoad.getString("Envoy.Summoner.Item.Material")), itemAmount, (short) configLoad.getInt("Envoy.Summoner.Item.Data"));
                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Envoy.Summoner.Displayname")));
                if (configLoad.getString("Envoy.Summoner.Lore") != null) {
                    List<String> itemLore = new ArrayList<>();
                    for (String itemLoreLine : configLoad.getStringList("Envoy.Summoner.Lore"))
                        itemLore.add(ChatColor.translateAlternateColorCodes('&', itemLoreLine));
                    im.setLore(itemLore);
                }
                is.setItemMeta(im);
                is = NBTUtil.addTag(is, "Envoy", "Envoy");
                if (configLoad.getBoolean("Envoy.Summoner.Full.Drop")) {
                    if (targetPlayer.getInventory().firstEmpty() == -1) {
                        targetPlayer.getWorld().dropItemNaturally(targetPlayer.getLocation(), is);
                    } else {
                        targetPlayer.getInventory().addItem(is);
                    }
                } else {
                    if (targetPlayer.getInventory().firstEmpty() == -1) {
                        if (player.getUniqueId().equals(targetPlayer.getUniqueId())) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Commands.Envoy.Admin.GiveSummoner.Full.Yourself.Message")));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Commands.Envoy.Admin.GiveSummoner.Full.Other.Message")
                                    .replace("%player", targetPlayer.getName())));
                        }
                        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                        return true;
                    }
                    targetPlayer.getInventory().addItem(is);
                }
                if (player.getUniqueId().equals(targetPlayer.getUniqueId())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.GiveSummoner.Given.Yourself.Message")
                            .replace("%amount", String.valueOf(itemAmount))));
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Commands.Envoy.Admin.GiveSummoner.Given.Other.Message")
                            .replace("%player", targetPlayer.getName()).replace("%amount", String.valueOf(itemAmount))));
                }
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            } else if (args[1].equalsIgnoreCase("reload")) {
                fileManager.getConfig(new File(this.instance.getDataFolder(), "config.yml")).loadFile();
                fileManager.getConfig(new File(this.instance.getDataFolder(), "envoy.yml")).loadFile();
                fileManager.getConfig(new File(this.instance.getDataFolder(), "locations.yml")).loadFile();
                envoyManager.removeCrates();
                envoyManager.setupEnvoy();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Admin.Reload.Reloaded.Message")));
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                        .getString("Commands.Envoy.Argument.Message")));
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                    .getString("Commands.Envoy.Argument.Message")));
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> matches = new ArrayList<>();
        if (!(sender instanceof Player))
            return matches;
        if (args.length == 1) {
            List<String> availableArguments = new ArrayList<>();
            availableArguments.add("help");
            availableArguments.add("top");
            availableArguments.add("stats");
            for (String availableArgument : availableArguments) {
                if (availableArgument.toLowerCase().startsWith(args[0].toLowerCase()))
                    matches.add(availableArgument);
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("stats")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                    matches.add(all.getName());
            }
        }
        return matches;
    }
}
