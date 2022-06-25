package me.kingsavaqe.astroenvoy.menus;


import java.io.File;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.envoy.Envoy;
import me.kingsavaqe.astroenvoy.utils.inventory.nInventoryUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LootMenu {
    private static LootMenu instance;

    public static LootMenu getInstance() {
        if (instance == null)
            instance = new LootMenu();
        return instance;
    }

    public void open(final Player player, final int page) {
        AstroEnvoyPlugin instance = AstroEnvoyPlugin.getInstance();
        final FileConfiguration configLoad = instance.getFileManager().getConfig(new File(instance.getDataFolder(), "config.yml")).getFileConfiguration();
        Envoy envoy = instance.getEnvoyManager().getEnvoy();
        nInventoryUtil inv = new nInventoryUtil(player, new nInventoryUtil.ClickEventHandler() {
            public void onClick(nInventoryUtil.ClickEvent event) {
                ItemStack is = event.getItem();
                event.setWillClose(false);
                event.setWillDestroy(false);
                if (is.getType() == Material.FENCE_GATE) {
                    EnvoyMenu.getInstance().open(player);
                    player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
                } else if (is.getType() == Material.BARRIER && (is.getItemMeta().getDisplayName()
                        .equals(ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Loot.Items.Next.Unavailable.Displayname"))) || is
                        .getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Loot.Items.Previous.Unavailable.Displayname"))))) {
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                } else if (is.getType() == Material.ARROW) {
                    if (is.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Menus.Loot.Items.Next.Available.Displayname")))) {
                        LootMenu.this.open(player, page + 1);
                    } else if (is.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Menus.Loot.Items.Previous.Available.Displayname")))) {
                        LootMenu.this.open(player, page - 1);
                    }
                    player.playSound(player.getLocation(), Sound.ARROW_HIT, 1.0F, 1.0F);
                } else if (is.getType() == Material.ITEM_FRAME) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
                } else if (is.getType() == Material.STAINED_GLASS_PANE) {
                    player.playSound(player.getLocation(), Sound.GLASS, 1.0F, 1.0F);
                } else if (is.getType() == Material.BARRIER && is
                        .getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Loot.Items.None.Displayname")))) {
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                } else {
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
                }
            }
        });
        inv.setTitle(ChatColor.translateAlternateColorCodes('&', configLoad.getString("Menus.Loot.Title")));
        inv.setRows(6);
        inv.addItem(inv
                .createItem(new ItemStack(Material.FENCE_GATE),
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Loot.Items.Return.Displayname")), null, null, null, null), 0, 8);
        inv.addItem(inv
                .createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15),
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Loot.Items.Glass.Displayname")), null, null, null, null), 9, 10, 11, 12, 13, 14, 15, 16, 17);
        int index = page * 36 - 36;
        int endIndex = (index >= envoy.getLootItems().size()) ? (envoy.getLootItems().size() - 1) : (index + 36);
        int invSlot = 17, nextEndIndex = 0;
        if (envoy.hasLootItems()) {
            for (; index < endIndex; index++) {
                if (envoy.getLootItems().size() > index) {
                    invSlot++;
                    inv.addItemStack(envoy.getLootItems().get(index).getItemStack(), invSlot);
                }
            }
            nextEndIndex = envoy.getLootItems().size() - page * 36;
        } else {
            inv.addItem(inv.createItem(new ItemStack(Material.BARRIER),
                    ChatColor.translateAlternateColorCodes('&', configLoad
                            .getString("Menus.Loot.Items.None.Displayname")), configLoad
                            .getStringList("Menus.Loot.Items.None.Lore"), null, null, null), 31);
        }
        if (page == 1) {
            inv.addItem(inv.createItem(new ItemStack(Material.BARRIER), configLoad
                    .getString("Menus.Loot.Items.Previous.Unavailable.Displayname"), null, null, null, null), 1);
        } else {
            inv.addItem(inv.createItem(new ItemStack(Material.ARROW), configLoad
                    .getString("Menus.Loot.Items.Previous.Available.Displayname"), null, null, null, null), 1);
        }
        if (nextEndIndex == 0 || nextEndIndex < 0) {
            inv.addItem(inv.createItem(new ItemStack(Material.BARRIER), configLoad
                    .getString("Menus.Loot.Items.Next.Unavailable.Displayname"), null, null, null, null), 7);
        } else {
            inv.addItem(inv.createItem(new ItemStack(Material.ARROW), configLoad
                    .getString("Menus.Loot.Items.Next.Available.Displayname"), null, null, null, null), 7);
        }
        inv.open();
    }
}
