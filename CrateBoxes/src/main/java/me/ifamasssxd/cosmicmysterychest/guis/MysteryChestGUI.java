/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package me.ifamasssxd.cosmicmysterychest.guis;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.CustomLoot;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MysteryChestGUI
extends CosmicGUI {
    private int animationTick;
    private int MAX_ANIMATION_TICKS = 160;
    private BukkitTask animationTask;
    private MysteryChest chest;
    private Long expiration;
    private boolean finished = false;
    private boolean readyToGive = false;
    private List<CustomLoot> lastPrizesShown;
    ItemStack singlePane = new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data((short)DyeColor.GRAY.getWoolData()).build();

    public MysteryChestGUI(Player player, MysteryChest chest, Long expiration) {
        super(player, "Astro Lootbox", 9);
        this.chest = chest;
        this.expiration = expiration;
    }

    public void skipGUI(JavaPlugin shard) {
        this.givePrize(true);
    }

    public void openGUI(JavaPlugin owning) {
        super.openGUI(owning);
        this.startAnimation();
    }

    public int getSecondsLeft() {
        return (int)Math.ceil((double)(this.MAX_ANIMATION_TICKS - this.animationTick) / 20.0);
    }

    private void startAnimation() {
        if (this.animationTask != null && Bukkit.getScheduler().isCurrentlyRunning(this.animationTask.getTaskId())) {
            this.animationTask.cancel();
        }
        this.animationTask = new BukkitRunnable(){

            public void run() {
                if (!MysteryChestGUI.this.player.isValid() || MysteryChestGUI.this.player.getOpenInventory() == null || !MysteryChestGUI.this.inventory.equals((Object)MysteryChestGUI.this.player.getOpenInventory().getTopInventory())) {
                    this.cancel();
                    Bukkit.getLogger().info("Cancelling task for " + MysteryChestGUI.this.player.getName() + " due to not having inventory open!");
                    return;
                }
                if (MysteryChestGUI.this.animationTick > MysteryChestGUI.this.MAX_ANIMATION_TICKS) {
                    this.cancel();
                    MysteryChestGUI.this.readyToGive = true;
                    MysteryChestGUI.this.player.playSound(MysteryChestGUI.this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.5f);
                    MysteryChestGUI.this.inventory.remove(Material.STAINED_GLASS_PANE);
                    MysteryChestGUI.this.player.updateInventory();
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicMysteryChest.get(), () -> {
                        if (MysteryChestGUI.this.player.isValid() && MysteryChestGUI.this.player.getOpenInventory() != null && MysteryChestGUI.this.inventory.equals((Object)MysteryChestGUI.this.player.getOpenInventory().getTopInventory()) && MysteryChestGUI.this.readyToGive) {
                            MysteryChestGUI.this.readyToGive = false;
                            MysteryChestGUI.this.givePrize(true);
                            MysteryChestGUI.this.player.closeInventory();
                        }
                    }, 100L);
                    return;
                }
                if (MysteryChestGUI.this.animationTick < MysteryChestGUI.this.MAX_ANIMATION_TICKS) {
                    if (MysteryChestGUI.this.animationTick < 30) {
                        MysteryChestGUI.this.player.playSound(MysteryChestGUI.this.player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.92f);
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            MysteryChestGUI.this.player.playSound(MysteryChestGUI.this.player.getLocation(), Sound.WOOD_CLICK, 1.0f, 1.1f);
                        }
                        MysteryChestGUI.this.redraw();
                    } else {
                        int ticksToSkip = 0;
                        int n = MysteryChestGUI.this.animationTick > 80 ? 4 : (MysteryChestGUI.this.animationTick > 60 ? 3 : (ticksToSkip = MysteryChestGUI.this.animationTick > 40 ? 2 : 2));
                        if (MysteryChestGUI.this.animationTick % ticksToSkip == 0) {
                            MysteryChestGUI.this.redraw();
                            MysteryChestGUI.this.player.playSound(MysteryChestGUI.this.player.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.92f);
                            if (ThreadLocalRandom.current().nextBoolean()) {
                                MysteryChestGUI.this.player.playSound(MysteryChestGUI.this.player.getLocation(), Sound.WOOD_CLICK, 1.0f, 1.1f);
                            }
                        }
                    }
                }
                MysteryChestGUI.this.animationTick++;
                if (MysteryChestGUI.this.animationTick >= MysteryChestGUI.this.MAX_ANIMATION_TICKS) {
                    MysteryChestGUI.this.readyToGive = true;
                }
            }
        }.runTaskTimer((Plugin)CosmicMysteryChest.get(), 20L, 1L);
    }

    private void givePrize(boolean announce) {
        if (this.finished) {
            return;
        }
        this.finished = true;
        this.readyToGive = false;
        if (this.lastPrizesShown == null) {
            this.fillRandomPrizes();
        }
        if (announce) {
            boolean hasUltraRare = false;
            for (CustomLoot given : this.lastPrizesShown) {
                double chance;
                if (given.getChance() == -1.0 && !given.isJackpotItem() || !((chance = given.getChance() / this.chest.getLootTable().getTotalWeight()) <= 0.05) && !given.isJackpotItem()) continue;
                hasUltraRare = true;
                break;
            }
            for (Player pl : Bukkit.getOnlinePlayers()) {
                boolean self = pl.getUniqueId().equals(this.player.getUniqueId());
                if ((self || !WorldGuardUtils.isPvPEnabled((Location)pl.getLocation()) || pl.getWorld().getName().equals(this.player.getWorld().getName()) && pl.getLocation().distanceSquared(this.player.getLocation()) <= 4096.0) && !self && !hasUltraRare) continue;
            }
        }
        this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.1f, 0.9f);
        for (CustomLoot loot : this.lastPrizesShown) {
            long id;
            ItemStack item3 = loot.getItem().clone();
            if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && ((id = ItemID.getItemID((ItemStack)item3)) > 0L || ItemID.needsItemID((ItemStack)item3))) {
                if (id > 0L) {
                    item3 = ItemID.removeItemID((ItemStack)item3);
                }
                if (item3.getAmount() > 1) {
                    for (int i = 0; i < item3.getAmount(); ++i) {
                        ItemStack clone = ItemID.assignItemID(null, (ItemStack)item3.clone());
                        clone.setAmount(1);
                        PlayerUtil.giveOrDropProtectedItem((Player)this.player, (ItemStack)clone, (int)-1);
                        Bukkit.getLogger().info("[CosmicMysteryChest] Giving: " + ItemUtils.getDisplayName((ItemStack)clone) + " to " + this.player.getName() + ", chance: " + loot.getChance() + " / " + this.chest.getLootTable().getTotalWeight() + ", total chance: " + loot.getChance() / this.chest.getLootTable().getTotalWeight() * 100.0 + "!");
                    }
                    continue;
                }
                item3 = ItemID.assignItemID(null, (ItemStack)item3);
            }
            PlayerUtil.giveOrDropProtectedItem((Player)this.player, (ItemStack)item3, (int)-1);
            Bukkit.getLogger().info("[CosmicMysteryChest] Giving: " + ItemUtils.getDisplayName((ItemStack)item3) + " to " + this.player.getName() + ", chance: " + loot.getChance() + " / " + this.chest.getLootTable().getTotalWeight() + ", total chance: " + loot.getChance() / this.chest.getLootTable().getTotalWeight() * 100.0 + "!");
        }
        this.player.updateInventory();
        Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(this.player, "MYSTERY_CHEST_OPEN", new Object[]{Lists.newArrayList(this.lastPrizesShown), this.chest.getInternalName()}));
    }

    public void onInventoryClose() {
        if (!this.finished && this.readyToGive) {
            this.givePrize(true);
        } else if (!this.finished) {
            this.finished = true;
            if (!PlayerUtil.giveOrDropProtectedItem((Player)this.player, (ItemStack)this.chest.createClaimableItem(true, this.expiration), (int)-1)) {
                this.player.sendMessage(CC.Red + "Your inventory was full so your Lootbox has been dropped at your feet!");
            }
        }
        super.onInventoryClose();
    }

    public List<CustomLoot> fillRandomPrizes() {
        if (this.lastPrizesShown == null) {
            this.lastPrizesShown = Lists.newLinkedList();
        }
        this.lastPrizesShown.clear();
        for (int i = 0; i < this.chest.getItemBundleSize(); ++i) {
            this.lastPrizesShown.add(this.chest.getRandomPrize());
        }
        if (this.chest.getGarunteedItems() != null) {
            for (ItemStack item : this.chest.getGarunteedItems()) {
                this.lastPrizesShown.add(new CustomLoot(item, -1.0, false));
            }
        }
        return this.lastPrizesShown;
    }

    public void redraw() {
        int secondsLeft = this.getSecondsLeft();
        if (secondsLeft > 0 && !this.readyToGive) {
            ItemStack pane = new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").amount(secondsLeft).data(this.readyToGive ? (short)DyeColor.LIME.getWoolData() : (short)DyeColor.GRAY.getWoolData()).build();
            for (int i = 0; i < this.size; ++i) {
                this.setItem(i, i == 0 || i == this.size - 1 ? pane : this.singlePane, null);
            }
        } else {
            this.inventory.clear();
            this.getInventoryItems().clear();
        }
        this.fillRandomPrizes();
        int size = this.lastPrizesShown.size();
        int index = size == 1 ? 4 : (size == 3 ? 3 : (size == 5 ? 2 : 2));
        Runnable clickRunnable = () -> this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must wait to claim this item!");
        for (CustomLoot item : this.lastPrizesShown) {
            this.setItem(index++, new InventoryItem(item.getItem()).click(clickRunnable));
        }
        this.player.updateInventory();
    }
}

