package me.ifamasssxd.cosmicbosses.commands;

import me.ifamasssxd.cosmicbosses.boss.type.Boss;
import me.ifamasssxd.cosmicbosses.boss.type.Bosses;
import me.ifamasssxd.cosmicbosses.utils.MiscUtil;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandBossEgg implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("lootredeem")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player pl = (Player)sender;
            if (!pl.hasMetadata("lootredeem")) {
                pl.sendMessage((Object)((Object) ChatColor.RED) + "You have no pending boss rewards to claim!");
                return true;
            }
            if (pl.getInventory().firstEmpty() == -1) {
                pl.sendMessage((Object)((Object)ChatColor.RED) + "You have no space available to claim your boss reward!");
                return true;
            }
            ItemStack is = (ItemStack)pl.getMetadata("lootredeem").get(0).value();
            pl.removeMetadata("lootredeem", CosmicBosses.getInstance());
            pl.getInventory().addItem(is);
            pl.sendMessage((Object)((Object)ChatColor.YELLOW) + "You have recieved your boss rewards!");
            pl.playSound(pl.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
        }
        if (cmd.getName().equalsIgnoreCase("givecbe")) {
            String p_name = null;
            if (sender instanceof Player && !((Player)sender).isOp()) {
                return true;
            }
            String string = args.length == 2 ? args[1] : (p_name = sender instanceof Player ? ((Player)sender).getName() : null);
            if (p_name == null) {
                Bukkit.getLogger().info("/givecbe [slime,irongolem,blaze,wither] <player>");
                return true;
            }
            if (args.length < 1) {
                if (sender instanceof Player) {
                    ((Player)sender).sendMessage((Object)((Object)ChatColor.RED) + "/givecbe [1-4] <player>");
                } else {
                    Bukkit.getLogger().info("/givecbe [slime,irongolem,blaze,wither] <player>");
                }
                return true;
            }
            if (Bukkit.getPlayer(p_name) == null) {
                if (sender instanceof Player) {
                    ((Player)sender).sendMessage((Object)((Object)ChatColor.RED) + p_name + " cannot be found.");
                } else {
                    Bukkit.getLogger().info(p_name + " cannot be found.");
                }
                return true;
            }
            Player ply = Bukkit.getPlayer(p_name);
            if (ply.getInventory().firstEmpty() == -1) {
                if (sender instanceof Player) {
                    ((Player)sender).sendMessage((Object)((Object)ChatColor.RED) + p_name + " has no inventory space.");
                } else {
                    Bukkit.getLogger().info(p_name + " has no inventory space.");
                }
                return true;
            }
            Boss b = Bosses.valueOf((String)args[0].toUpperCase()).b;
            ChatColor cc = CosmicSpaceChestsAPI.getColor((ItemRarity)CosmicSpaceChestsAPI.getItemRarity((int)b.rarity.i));
            ItemStack is = MiscUtil.getBossEgg(b);
            ply.getInventory().addItem(is);
            ply.sendMessage((Object)((Object)ChatColor.GRAY) + "You got a " + (Object)((Object)cc) + (Object)((Object)ChatColor.UNDERLINE) + b.custom_name + " Spawner egg!");
            ply.updateInventory();
            if (sender instanceof Player && !((Player)sender).getName().equalsIgnoreCase(ply.getName())) {
                ((Player)sender).sendMessage((Object)((Object)ChatColor.GRAY) + p_name + " has been given a " + (Object)((Object)cc) + (Object)((Object)ChatColor.UNDERLINE) + b.custom_name + " Spawner egg!");
            } else {
                Bukkit.getLogger().info(p_name + " has been given 1x " + (Object)((Object)cc) + (Object)((Object)ChatColor.UNDERLINE) + b.custom_name + " Spawner egg!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("cbe")) {
            if (sender instanceof Player && !((Player)sender).isOp()) {
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("egg")) {
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.SLIME.b));
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.IRONGOLEM.b));
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.BLAZE.b));
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.WITHER.b));
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.HEADLESS_KING.b));
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.PIG_ZOMBIE.b));
                    ((Player)sender).getInventory().addItem(MiscUtil.getBossEgg(Bosses.ELF.b));
                }
                if (args[0].equalsIgnoreCase("clear")) {
                    int i = 0;
                    for (Entity ent : Boss.boss_map.keySet()) {
                        LivingEntity le = (LivingEntity)ent;
                        le.damage(le.getHealth());
                        ++i;
                    }
                    sender.sendMessage("Killed " + i + " bosses.");
                }
            }
        }
        return true;
    }
}
