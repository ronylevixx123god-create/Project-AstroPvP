package org.arkhamnetwork.enchantments.menus;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.HCFOutpostUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import org.arkhamnetwork.Arkkit.libs.ExpUtils;
import org.arkhamnetwork.enchantments.libs.EnchanterRarity;
import org.arkhamnetwork.enchantments.util.TieredEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class EnchanterGUI extends CosmicGUI {
    public EnchanterGUI(Player player) {
        super(player, "Astro Enchanter", 9);
    }

    public void redraw() {
        boolean capsInfernal = HCFOutpostUtils.doesControlOutpost(this.player, "infernal");
        int index = 0;
        for (EnchanterRarity rarity : EnchanterRarity.values()) {
            int xpPrice = (int)rarity.getPrice();
            String realPriceString = NumberUtils.formatMoney(rarity.getPrice());
            int adjustedPrice = capsInfernal ? (int)(xpPrice * 0.9D) : xpPrice;
            setItem(index++, (new ItemBuilder(Material.STAINED_GLASS_PANE, rarity.getColor() + CC.Bold + rarity.getName() + " Enchantment" + CC.Gray + " (Right Click)", CC.Gray + "Examine to receive a random", rarity

                    .getColor() + rarity.getName().toLowerCase() + CC.Gray + " enchantment book.", "", CC.Gray + "Use " + rarity

                    .getColor() + "/help enchants " + rarity.getName().toLowerCase() + CC.Gray + " to view a list", CC.Gray + "of possible enchantments you could unlock!", "", CC.AquaB + "COST " + CC.White + (capsInfernal ? (CC.White + CC.Strike + realPriceString + CC.White + " -> " + CC.Aqua +

                    NumberUtils.formatMoney(adjustedPrice) + CC.White + " EXP " + CC.Gray + "(Infernal Stronghold)") : (realPriceString + " EXP")))).data(
                    (short)rarity.getPaneColor().getWoolData()).build(), () -> {
                int playerXp = ExpUtils.getTotalExperience(this.player);
                int price = (int)rarity.getPrice();
                int newAdjustedPrice = HCFOutpostUtils.doesControlOutpost(this.player, "infernal") ? (int)(price * 0.9D) : price;
                if (newAdjustedPrice > playerXp) {
                    this.player.sendMessage(ChatColor.RED + "You do not have enough EXP to purchase that.");
                    this.player.sendMessage(ChatColor.GRAY + "Your XP: " + playerXp);
                    this.player.closeInventory();
                    return;
                }
                if (this.player.getInventory().firstEmpty() == -1) {
                    this.player.sendMessage(ChatColor.RED + "You do not have any room in your inventory.");
                    this.player.closeInventory();
                    return;
                }
                ExpUtils.setTotalExperience(this.player, playerXp - newAdjustedPrice);
                this.player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + NumberUtils.formatMoney(newAdjustedPrice) + " xp");
                this.player.getInventory().addItem(TieredEnchantments.getMysteryEnchantmentBook(rarity.getTier()));
                this.player.playSound(this.player.getLocation(), Sound.VILLAGER_HAGGLE, 1.0F, 0.44F);
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "contest add " + this.player.getName() + " enchanterXP " + newAdjustedPrice);
                if (Bukkit.getPluginManager().isPluginEnabled("CosmicFactionQuests"))
                    if (rarity.getTier() == 5) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "fquests add LEGENDARY_ENCHANTER " + this.player.getName());
                    } else if (rarity.getTier() == 4) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "fquests add ULTIMATE_ENCHANTER " + this.player.getName());
                    }
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(this.player, "ENCHANTER_BUY", new Object[] { rarity.name(), newAdjustedPrice}));
            });
        }
    }
}
