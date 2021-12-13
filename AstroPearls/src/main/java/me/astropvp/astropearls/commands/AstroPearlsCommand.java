package me.astropvp.astropearls.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropearls.AstroPearls;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;


public class AstroPearlsCommand implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("*")) {
            return false;
        } else {
            if (args.length == 0) {
                this.sendUsage(sender);
            } else if (args.length == 2) {
                Player player = Bukkit.getPlayer(args[1]);
                if (args[0].equals("resetcooldown") && player != null) {
                    if (sender.hasPermission("coralpearls.admin")) {
                        AstroPearls.getInstance().getTimer().cooldown.remove(player.getUniqueId());
                        sender.sendMessage("You have removed the cooldown of " + player.getName());
                        if (AstroPearls.getInstance().getConfig().getBoolean("PearlRefund.PearlRefund-Command")) {
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), AstroPearls.getInstance().getConfig().getString("PearlRefund.Command").replace("%player%", player.getName()));
                        }
                    } else {
                        sender.sendMessage(CC.translate("&cNo permission."));
                    }
                } else if (args[0].equals("resetcooldown") && player == null) {
                    sender.sendMessage(CC.translate("&cInvalid Player"));
                }
            } else if (args.length == 1) {
                if (args[0].equals("slabs")) {
                    if (sender.hasPermission("coralpearls.admin")) {
                        if (AstroPearls.getInstance().getConfig().getBoolean("PearlMethods.Slabs")) {
                            sender.sendMessage(CC.translate("&b&lTalibanPearl&b on &dSlabs &bhave been turned &coff!"));
                            AstroPearls.getInstance().getConfig().set("PearlMethods.Slabs", false);
                            AstroPearls.getInstance().getconfigfile().save();
                            AstroPearls.getInstance().getconfigfile().load();
                        } else if (!AstroPearls.getInstance().getConfig().getBoolean("PearlMethods.Slabs")) {
                            sender.sendMessage(CC.translate("&b&lTalibanPearl&b on &dSlabs &bhave been turned &aon!"));
                            AstroPearls.getInstance().getConfig().set("PearlMethods.Slabs", true);
                            AstroPearls.getInstance().getconfigfile().save();
                            AstroPearls.getInstance().getconfigfile().load();
                        }
                    } else {
                        sender.sendMessage(CC.translate("&cNo permission."));
                    }
                } else if (args[0].equals("stairs")) {
                    if (sender.hasPermission("coralpearls.admin")) {
                        if (AstroPearls.getInstance().getConfig().getBoolean("PearlMethods.Stairs")) {
                            sender.sendMessage(CC.translate("&b&lTalibanPearl&b on &dStairs &bhave been turned &coff!"));
                            AstroPearls.getInstance().getConfig().set("PearlMethods.Stairs", false);
                            AstroPearls.getInstance().getconfigfile().save();
                            AstroPearls.getInstance().getconfigfile().load();
                        } else if (!AstroPearls.getInstance().getConfig().getBoolean("PearlMethods.Stairs")) {
                            sender.sendMessage(CC.translate("&b&lTalibanPearl&b on &dStairs &bhave been turned &aon!"));
                            AstroPearls.getInstance().getConfig().set("PearlMethods.Stairs", true);
                            AstroPearls.getInstance().getconfigfile().save();
                            AstroPearls.getInstance().getconfigfile().load();
                        }
                    } else {
                        sender.sendMessage(CC.translate("&cNo permission."));
                    }
                } else if (args[0].equals("reload")) {
                    if (sender.hasPermission("coralpearls.admin")) {
                        AstroPearls.getInstance().reloadConfig();
                        AstroPearls.getInstance().getconfigfile().load();
                        sender.sendMessage(CC.translate("&cconfig.yml has been reloaded!"));
                    } else {
                        sender.sendMessage(CC.translate("&cNo permission."));
                    }
                } else if (args[0].equals("tp") || args[0].equals("teleport")) {
                    if (sender.hasPermission("coralpearls.admin")) {
                        if (AstroPearls.getInstance().getConfig().getBoolean("Settings.TaliPearlTP")) {
                            sender.sendMessage(CC.translate("&b&lTalibanPearl&b on &dTeleport &bhave been turned &coff!"));
                            AstroPearls.getInstance().getConfig().set("Settings.TaliPearlTP", false);
                            AstroPearls.getInstance().getconfigfile().save();
                            AstroPearls.getInstance().getconfigfile().load();
                        } else if (!AstroPearls.getInstance().getConfig().getBoolean("Settings.TaliPearlTP")) {
                            sender.sendMessage(CC.translate("&b&lTalibanPearl&b on &dTeleport &bhave been turned &aon!"));
                            AstroPearls.getInstance().getConfig().set("Settings.TaliPearlTP", true);
                            AstroPearls.getInstance().getconfigfile().save();
                            AstroPearls.getInstance().getconfigfile().load();
                        }
                    } else {
                        sender.sendMessage(CC.translate("&cNo permission."));
                    }
                }
            } else {
                this.sendUsage(sender);
            }

            return false;
        }
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(CC.translate("&8&m------------------- &b&lExodus Pearls &8&m-------------------"));
        sender.sendMessage("");
        sender.sendMessage(CC.translate("Commands:"));
        sender.sendMessage(CC.translate("&b/axpearl &8-&7 Displays this message."));
        sender.sendMessage(CC.translate("&b/axpearl slabs &8-&7 Toggles through pearling slabs."));
        sender.sendMessage(CC.translate("&b/axpearl stairs &8-&7 Toggles through pearling stairs."));
        sender.sendMessage(CC.translate("&b/axpearl tp &8-&7 Toggles pearling tp method."));
        sender.sendMessage(CC.translate("&b/axpearl resetcooldown <player> &8-&7 Resets a player's enderpearl cooldown."));
        sender.sendMessage(CC.translate("&b/axpearl reload &8-&7 Reloads the config.yml."));
        sender.sendMessage("");
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1)
            return Arrays.asList("slabs", "stairs", "tp", "resetcooldown", "reload", "cpearl");
        if (args.length == 2) {
            Player[] arrayOfPlayer = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
            int i = arrayOfPlayer.length;
            byte b = 0;
            if (b < i) {
                Player player = arrayOfPlayer[b];
                return Collections.singletonList(player.getName());
            }
        }
        return Collections.emptyList();
    }
}
