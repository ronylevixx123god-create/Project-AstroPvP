package me.ily2bb.slotbot.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ily2bb.slotbot.SlotBotPlugin;
import me.ily2bb.slotbot.objects.ItemListCacher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Inventories {
    public static Inventory createLootboxInventory(final boolean showPercentage) {
        final List<String> list = (List<String>) SlotBotPlugin.getInstance().getConfig().getList("me.astropvp.cratekeys.items");
        final int len = list.size() + 9;
        final int size = (len <= 9) ? 9 : ((len <= 18) ? 18 : ((len <= 27) ? 27 : ((len <= 36) ? 36 : ((len <= 45) ? 45 : ((len <= 54) ? 54 : 54)))));
        final Inventory inventory = Bukkit.createInventory(null, size, CC.DarkGray + "Astro Slot Bot Loot");
        for (int i = 0; i < list.size(); ++i) {
            final String l = list.get(i);
            final ItemStack x = Utils.getItem(l);
            if (showPercentage) {
                final ItemMeta meta = x.getItemMeta();
                final List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
                lore.add("");
                lore.add(CC.GreenB + CC.translate("CHANCE: &a&n") + Utils.getPercentage(l) + CC.translate("&a%"));
                lore.add("");
                meta.setLore(lore);
                x.setItemMeta(meta);
            }
            inventory.setItem(i, x);
        }
        inventory.setItem(size - 5, LootTable.createBackButton());
        for (int i = 0; i < inventory.getSize(); ++i) {
            final ItemStack x2 = inventory.getItem(i);
            if (x2 == null) {
                inventory.setItem(i, LootTable.createPane(7));
            }
        }
        return inventory;
    }

    public static Inventory createInventory(final int slots) {
        final Inventory inventory = Bukkit.createInventory(null, 54, CC.DarkGray + "Astro Slot Bot");
        final ItemStack item = LootTable.createLootTable();
        inventory.setItem(0, item);
        for (int i = 1; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane(15));
            }
        }
        for (int i = 7; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane(15));
            }
        }
        inventory.setItem(20, LootTable.createRewardSlot());
        inventory.setItem(21, LootTable.createRewardSlot());
        inventory.setItem(22, LootTable.createRewardSlot());
        inventory.setItem(23, LootTable.createRewardSlot());
        inventory.setItem(24, LootTable.createRewardSlot());
        inventory.setItem(26, LootTable.createSpinTicket(slots >= 1));
        inventory.setItem(53, LootTable.createHowToPlayBook());
        inventory.setItem(51, LootTable.createRoller(5 - slots));
        inventory.setItem(50, LootTable.createRoller(4 - slots));
        inventory.setItem(49, LootTable.createRoller(3 - slots));
        inventory.setItem(48, LootTable.createRoller(2 - slots));
        inventory.setItem(47, LootTable.createRoller(1 - slots));
        inventory.setItem(45, LootTable.createReturnSlotBotsButton(slots));
        for (int i = 2; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane((slots >= 1) ? 0 : 8));
            }
        }
        for (int i = 3; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane((slots >= 2) ? 0 : 8));
            }
        }
        for (int i = 4; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane((slots >= 3) ? 0 : 8));
            }
        }
        for (int i = 5; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane((slots >= 4) ? 0 : 8));
            }
        }
        for (int i = 6; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane((slots >= 5) ? 0 : 8));
            }
        }
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane(7));
            }
        }
        return inventory;
    }

    public static Inventory createRollingInventory(final Player player, final int slots) {
        final Inventory inventory = Bukkit.createInventory(null, 54, CC.DarkGray + "Astro Slot Bot");
        final ItemStack item = LootTable.createLootTable();
        inventory.setItem(0, item);
        for (int i = 1; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane(15));
            }
        }
        for (int i = 7; i < inventory.getSize() - 9; i += 9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane(15));
            }
        }
        inventory.setItem(53, LootTable.createHowToPlayBook());
        inventory.setItem(51, (slots >= 1) ? LootTable.createRoller(4) : null);
        inventory.setItem(49, (slots >= 2) ? LootTable.createRoller(3) : null);
        inventory.setItem(49, (slots >= 3) ? LootTable.createRoller(2) : null);
        inventory.setItem(49, (slots >= 4) ? LootTable.createRoller(1) : null);
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, LootTable.createPane(7));
            }
        }
        return inventory;
    }

    public static void startRollingInColumn(final Player player, final Inventory inventory, final int column) {
        player.setMetadata("slot_rolling_" + column, new FixedMetadataValue(SlotBotPlugin.getInstance(), null));
        final Object[] objects = Utils.getMap();
        final ItemStack item = Utils.getRandomItemFromLootTable(objects);
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(SlotBotPlugin.getInstance(), (Runnable) new BukkitRunnable() {
            final ItemStack[] previousItems = new ItemStack[6];
            int time = 45;

            public void run() {
                if (this.previousItems[0] == null) {
                    for (int i = 0; i < this.previousItems.length; ++i) {
                        this.previousItems[i] = Utils.getRandomItemFromLootTable(objects);
                    }
                } else {
                    for (int i = this.previousItems.length - 2; i >= 0; --i) {
                        this.previousItems[i + 1] = this.previousItems[i];
                    }
                    this.previousItems[0] = Utils.getRandomItemFromLootTable(objects);
                }
                int x = column;
                for (int j = 0; j < this.previousItems.length; ++j) {
                    inventory.setItem(x, this.previousItems[j]);
                    x += 9;
                }
                player.updateInventory();
                --this.time;
                if (this.time <= 0) {
                    x = column;
                    int iterations = 0;
                    for (int k = 0; k < this.previousItems.length; ++k) {
                        if (iterations != 2) {
                            inventory.setItem(x, null);
                        }
                        x += 9;
                        ++iterations;
                    }
                    for (int k = 18; k <= 26; ++k) {
                        ItemStack y = inventory.getItem(k);
                        if (y.getType() == Material.STAINED_GLASS_PANE) {
                            y = Utils.addGlow(y);
                            inventory.setItem(k, y);
                        }
                    }
                    player.removeMetadata("slot_rolling_" + column, SlotBotPlugin.getInstance());
                    ItemListCacher cacher = ItemListCacher.getCache(player);
                    boolean announce = false;
                    if (cacher == null) {
                        announce = true;
                    }
                    cacher = ((cacher == null) ? new ItemListCacher(player, this.previousItems[2]) : cacher.addItem(this.previousItems[2]));
                    if (announce) {
                        final ItemListCacher y2 = cacher;
                        Bukkit.getScheduler().runTaskLater(SlotBotPlugin.getInstance(), () -> y2.broadcastMessage(), 1L);
                    }
                    if (!Utils.isInventoryFull(player)) {
                        player.getInventory().addItem(this.previousItems[2]);
                    } else {
                        player.getWorld().dropItem(player.getLocation(), this.previousItems[2]);
                    }
                    this.cancel();
                } else if (!player.hasMetadata("slot_rolling_" + column)) {
                    ItemListCacher cacher2 = ItemListCacher.getCache(player);
                    boolean announce2 = false;
                    if (cacher2 == null) {
                        announce2 = true;
                    }
                    cacher2 = ((cacher2 == null) ? new ItemListCacher(player, item) : cacher2.addItem(item));
                    if (announce2) {
                        final ItemListCacher y3 = cacher2;
                        Bukkit.getScheduler().runTaskLater(SlotBotPlugin.getInstance(), () -> y3.broadcastMessage(), 1L);
                    }
                    if (!Utils.isInventoryFull(player)) {
                        player.getInventory().addItem(item);
                    } else {
                        player.getWorld().dropItem(player.getLocation(), item);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(SlotBotPlugin.getInstance(), 1L, 1L));
    }
}
