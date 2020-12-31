package net.astropvp.core.commands;

import net.astropvp.core.Main;
import net.astropvp.core.cooldown.PotsCooldown;
import net.astropvp.core.utils.ChatManager;
import net.astropvp.core.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PotionsCommand implements CommandExecutor {
    private Main plugin;
    private PotsCooldown cooldown = new PotsCooldown();
    private Utils utils = new Utils();

    public PotionsCommand(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Economy economy = Main.getEcon();
        if (!(sender instanceof Player)) {
            utils.console("&cCannot execute command from console.");
            return true;
        }
        Player p = (Player) sender;
        if (cooldown.getCooldown(p)) return false;

        int amount = 0;
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack == null || itemStack.getType() == Material.AIR)
                amount++;
        }

        if (amount == 0) {
            p.sendMessage(ChatColor.RED + "Your inventory is full. Please try again.");
            return true;
        }

        double cost = (amount * 55);
        if (economy.has(p, cost)) {

            economy.withdrawPlayer(p, cost);
            for (int i = 0; i < amount; i++) {
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.POTION, 1, (short) 16421)});
            }
            p.playSound(p.getLocation(), Sound.VILLAGER_YES, 10, 1);
            utils.sendMessage(p, "&c&l- $" + utils.format(cost));
            utils.sendMessage(p, "&a+" + amount + "&a Instant Health II Pots");
            cooldown.enterCooldown(p);
            return true;
        } else {
            utils.sendMessage(p, "&cYou don't have enough money, requires $" + utils.format(cost) + "!");
            return true;
        }
    }

}

