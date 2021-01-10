/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.util.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Snowball
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package com.compilex.CosmicCrate.listeners;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.CosmicCrateLootSelector;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class PlayerListener
implements Listener {
    private boolean isEmptyBesidesCCs(Inventory inventory, int allowed) {
        int found = 0;
        for (int i = 0; i < inventory.getSize(); ++i) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR || item.getType() != Material.ENDER_CHEST || CCAPI.getCosmicCrateVersion(item) == null) continue;
            ++found;
        }
        return found <= allowed;
    }

    private int getSlotsRequired(Player player, Version version) {
        int required = 0;
        List<ItemStack> item = CCAPI.getCosmicCrateLoot(player, version);
        if (item != null) {
            for (ItemStack reward : item) {
                long id = ItemID.getItemID((ItemStack)reward);
                if (reward.getAmount() > 1 && reward.getAmount() <= 16 && (id != -1L || ItemID.needsItemID((ItemStack)reward))) {
                    required += reward.getAmount();
                    continue;
                }
                ++required;
            }
        }
        return required;
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && CCAPI.getCosmicCrateVersion(e.getItem()) != null) {
            Version v = CCAPI.getCosmicCrateVersion(e.getItem());
            ItemStack i = e.getItem();
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getPlayer().isOp() && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) {
                Inventory previewInventory = Bukkit.createInventory(null, (int)45, (String)v.name());
                for (ItemStack is : v.getCrateData().getCrateLoot(e.getPlayer())) {
                    previewInventory.addItem(new ItemStack[]{is});
                }
                previewInventory.addItem(new ItemStack[]{v.getCrateData().getRandomMetaItem(e.getPlayer())});
                e.getPlayer().openInventory(previewInventory);
                return;
            }
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                int required = this.getSlotsRequired(e.getPlayer(), v);
                if (this.getEmptySlots(e.getPlayer()) < this.getSlotsRequired(e.getPlayer(), v)) {
                    e.getPlayer().sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have enough empty inventory slots.");
                    e.getPlayer().sendMessage((Object)ChatColor.GRAY + ChatColor.ITALIC.toString() + "REQ: " + required + "+ slots");
                    return;
                }
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    e.getPlayer().setItemInHand(i);
                } else {
                    long itemID;
                    e.getPlayer().setItemInHand(null);
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID((ItemStack)e.getItem())) != -1L) {
                        ItemID.blacklistIID((long)itemID, (String)("ccOpen/" + v.toString() + "/" + e.getPlayer().getName()));
                    }
                }
                if (v == Version.DECEMBER || v == Version.JANURARY || v == Version.FEBRUARY || v == Version.MARCH || v == Version.APRIL || v == Version.JULY || v == Version.AUGUST || v == Version.SCHOOL || v == Version.OCTOBER || v == Version.HALLOWEEN || v == Version.NOVEMBER || v == Version.BLACK_FRIDAY || v == Version.DECEMBER_2016 || v == Version.HOLIDAY || v == Version.JANUARY_2017 || v == Version.VALENTINE || v == Version.MARCH_2017 || v == Version.APRIL_2017 || v == Version.MAY_2017 || v == Version.JUNE_2017 || v == Version.JULY_2017 || v == Version.AUGUST_2017 || v == Version.SCHOOL_2017 || v == Version.SEPTEMBER_2017 || v == Version.OCTOBER_2017 || v == Version.HALLOWEEN_2017 || v == Version.NOVEMBER_2017 || v == Version.THANKSGIVING_2017 || v == Version.DECEMBER_2017 || v == Version.HOLIDAY_2017 || v == Version.JANUARY_2018 || v == Version.FEBUARY_2018 || v == Version.VALENTINE_2018 || v != null && v.getCrateData() != null) {
                    Player p = e.getPlayer();
                    Location l = p.getEyeLocation();
                    ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.44f, (int)100);
                    p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 2.0f);
                    CosmicCrateLootSelector ccls = new CosmicCrateLootSelector(e.getPlayer(), v);
                    e.getPlayer().setMetadata("ccrate_loot_selector", (MetadataValue)new FixedMetadataValue((Plugin)CosmicCrate.get(), (Object)ccls));
                    ccls.open();
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (e.getPlayer().getOpenInventory() != null && e.getPlayer().getOpenInventory().getTitle().startsWith("Cosmic Crate: ")) {
            e.setCancelled(true);
        }
    }

    private int getEmptySlots(Player p) {
        int i = 0;
        for (ItemStack is : p.getInventory()) {
            if (is != null && is.getType() != Material.AIR) continue;
            ++i;
        }
        return i;
    }

    @EventHandler
    public void onProjectileThrow(ProjectileLaunchEvent e) {
        Player p;
        Snowball s;
        if (e.getEntity() instanceof Snowball && !e.getEntity().getWorld().getName().contains("dungeon") && (s = (Snowball)e.getEntity()).getShooter() instanceof Player && CCAPI.isExplosiveSnowball((p = (Player)s.getShooter()).getItemInHand())) {
            s.setMetadata("explosive", (MetadataValue)new FixedMetadataValue((Plugin)CosmicCrate.get(), (Object)true));
        }
    }

    @EventHandler
    public void onProjectilHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Snowball && e.getEntity().hasMetadata("explosive")) {
            if (e.getEntity().getLocation().getBlock().getType() == Material.WATER || e.getEntity().getLocation().getBlock().getType() == Material.STATIONARY_WATER || e.getEntity().getLocation().getBlock().getType() == Material.CHEST) {
                return;
            }
            if (WorldGuardUtils.getRegionName((Location)e.getEntity().getLocation()).contains("warzone") || WorldGuardUtils.isPvPDisabled((Location)e.getEntity().getLocation())) {
                return;
            }
            Entity silverfish = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.SILVERFISH);
            for (Entity ent : e.getEntity().getNearbyEntities(4.0, 4.0, 4.0)) {
                if (!(ent instanceof LivingEntity) || WorldGuardUtils.isPvPDisabled((Location)ent.getLocation())) continue;
                this.pushAwayEntity(silverfish.getLocation(), ent, 3.8);
            }
            silverfish.remove();
            Location l = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)l, (float)0.1f, (int)50);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)l, (float)0.33f, (int)40);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.LARGE_EXPLODE, (Location)l, (float)0.2f, (int)5);
            l.getWorld().playSound(l, Sound.EXPLODE, 0.8f, 0.5f);
            l.getWorld().playSound(l, Sound.EAT, 1.0f, 0.77f);
        }
    }

    public void pushAwayEntity(Location center, Entity entity, double speed) {
        if (entity instanceof Horse) {
            return;
        }
        Vector unitVector = entity.getLocation().toVector().subtract(center.toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}

