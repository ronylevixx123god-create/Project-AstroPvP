/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.enums.armorsets.MotherOfYijki
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown
 *  org.arkhamnetwork.enchantments.enchantments.armor.Phoenix
 *  org.arkhamnetwork.enchantments.util.ArmorUtil
 *  org.arkhamnetwork.enchantments.util.GeneralUtil
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.MotherOfYijki;
import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulItemListener
implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerUseSoulPearl(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && this.isSoulPearl(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            Player p = e.getPlayer();
            if (!EnderpearlCooldown.isEnderpearlCooldownOver((Player)p) || !this.canThrowPearl(e.getItem(), p)) {
                return;
            }
            if (!KillTrackerAPI.hasSoulAmount((Player)p, (int)5)) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 5)" + (Object)ChatColor.BOLD + " ***");
                p.updateInventory();
                return;
            }
            KillTrackerAPI.removeSoulsFromGems((Player)p, (int)5);
            p.launchProjectile(EnderPearl.class);
            p.updateInventory();
        }
    }

    private boolean isSoulPearl(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_PEARL && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().equals(DungeonsRewardsAPI.getSoulPearl().getItemMeta().getLore());
    }

    private boolean canThrowPearl(ItemStack item, Player pl) {
        if (pl.hasMetadata("lastEnderpearlThrow")) {
            long lastEnderpearlThrow = ((MetadataValue)pl.getMetadata("lastEnderpearlThrow").get(0)).asLong();
            long cd = 400L;
            long timeSinceThrow = System.currentTimeMillis() - lastEnderpearlThrow;
            if (timeSinceThrow < 400L) {
                return false;
            }
        }
        return true;
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerUseSoulAnvil(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && this.isSoulAnvil(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            Player p = e.getPlayer();
            if (!KillTrackerAPI.hasSoulAmount((Player)p, (int)100)) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 100)" + (Object)ChatColor.BOLD + " ***");
                return;
            }
            if (p.hasMetadata("lastSoulAnvilUse") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSoulAnvilUse").get(0)).asLong() < 30000L) {
                p.sendMessage((Object)ChatColor.RED + "You cannot use Soul Anvil for another " + (Object)ChatColor.GRAY + (30000L - System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSoulAnvilUse").get(0)).asLong()) / 1000L + "s");
                return;
            }
            boolean damagedArmor = false;
            for (ItemStack armor : p.getInventory().getArmorContents()) {
                if (armor == null || armor.getDurability() == 0) continue;
                damagedArmor = true;
            }
            if (!damagedArmor) {
                p.sendMessage((Object)ChatColor.RED + "None of your equipped armor is currently damaged!");
                return;
            }
            p.setMetadata("lastSoulAnvilUse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)System.currentTimeMillis()));
            KillTrackerAPI.removeSoulsFromGems((Player)p, (int)100);
            if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getDurability() != 0) {
                p.getInventory().getHelmet().setDurability((short)Math.max(0.0, (double)p.getInventory().getHelmet().getDurability() - 0.1 * (double)p.getInventory().getHelmet().getType().getMaxDurability()));
            }
            if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getDurability() != 0) {
                p.getInventory().getChestplate().setDurability((short)Math.max(0.0, (double)p.getInventory().getChestplate().getDurability() - 0.1 * (double)p.getInventory().getChestplate().getType().getMaxDurability()));
            }
            if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getDurability() != 0) {
                p.getInventory().getLeggings().setDurability((short)Math.max(0.0, (double)p.getInventory().getLeggings().getDurability() - 0.1 * (double)p.getInventory().getLeggings().getType().getMaxDurability()));
            }
            if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getDurability() != 0) {
                p.getInventory().getBoots().setDurability((short)Math.max(0.0, (double)p.getInventory().getBoots().getDurability() - 0.1 * (double)p.getInventory().getBoots().getType().getMaxDurability()));
            }
            p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " -100 SOULS (" + (Object)ChatColor.WHITE + KillTrackerAPI.getAllSouls((Player)p) + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ")");
            p.sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + " +10% DURABILITY");
            p.playSound(p.getLocation(), Sound.ANVIL_USE, 0.7f, 1.2f);
        }
    }

    private boolean isSoulAnvil(ItemStack i) {
        return i != null && i.getType() == Material.ANVIL && i.getItemMeta().hasLore() && i.getItemMeta().getLore().equals(DungeonsRewardsAPI.getSoulAnvil().getItemMeta().getLore());
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerUseSpeedTrinket(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && this.isSpeedTrinket(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            Player p = e.getPlayer();
            if (p.hasMetadata("lastSpeedTrinketUse") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSpeedTrinketUse").get(0)).asLong() < 90000L) {
                p.sendMessage((Object)ChatColor.RED + "You cannot use a Speed Trinket for another " + (Object)ChatColor.GRAY + (90000L - System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSpeedTrinketUse").get(0)).asLong()) / 1000L + "s");
                return;
            }
            if (!KillTrackerAPI.hasSoulAmount((Player)p, (int)100)) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 100)" + (Object)ChatColor.BOLD + " ***");
                p.updateInventory();
                return;
            }
            KillTrackerAPI.removeSoulsFromGems((Player)p, (int)100);
            p.removePotionEffect(PotionEffectType.SPEED);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 3));
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.7f, 1.2f);
            p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " -100 SOULS (" + (Object)ChatColor.WHITE + KillTrackerAPI.getAllSouls((Player)p) + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ")");
            p.sendMessage((Object)ChatColor.DARK_AQUA + "" + (Object)ChatColor.BOLD + "* SPEED IV (7s) *");
            p.setMetadata("lastSpeedTrinketUse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)System.currentTimeMillis()));
            p.updateInventory();
        }
    }

    private boolean isSpeedTrinket(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("speedTrinket");
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerDamaged(EntityDamageEvent e) throws Exception {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= (double)ArmorUtil.getArmorDamageNullificationPercent((float)ArmorUtil.getArmorValue((Player)p));
            if (p.getHealth() > 0.0 && p.getHealth() - dmg <= 0.0) {
                boolean hasPhoenix = Phoenix.hasPhoenixEquipped((Player)p);
                int phoenixLevel = Phoenix.phoenixEquipped.getOrDefault(p.getUniqueId(), 0);
                if (hasPhoenix && KillTrackerAPI.hasSoulAmount((Player)p, (int)Phoenix.getPhoenixProcSoulCost((Player)p)) && Phoenix.canUsePhoenix((Player)p, (int)phoenixLevel)) {
                    return;
                }
                ItemStack phoenixFeather = null;
                for (ItemStack i : p.getInventory()) {
                    if (!this.isPhoenixFeather(i)) continue;
                    phoenixFeather = i;
                    break;
                }
                if (phoenixFeather != null) {
                    p.getInventory().remove(phoenixFeather);
                    e.setCancelled(true);
                    e.setDamage(0.0);
                    GeneralUtil.setPlayerHealth((LivingEntity)p, (double)p.getMaxHealth());
                    p.sendMessage("");
                    p.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "*** " + (Object)ChatColor.UNDERLINE + "PHOENIX FEATHER" + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + " ***");
                    p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- 1 Phoenix Feather");
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
                    for (Entity ent : p.getNearbyEntities(48.0, 48.0, 48.0)) {
                        if (!(ent instanceof Player)) continue;
                        ((Player)ent).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** PHOENIX FEATHER (" + (Object)ChatColor.GRAY + p.getName() + ", -1 phoenix feather" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ") ***");
                        ((Player)ent).playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
                    }
                    Location loc = p.getLocation();
                    de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), (Location)loc, (float)((float)Math.random()), (float)((float)Math.random()), (float)((float)Math.random()), (float)0.7f, (int)20);
                    de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), (Location)loc, (float)((float)Math.random()), (float)((float)Math.random()), (float)((float)Math.random()), (float)0.7f, (int)20);

                }
            }
        }
    }

    private boolean isPhoenixFeather(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("phoenixFeather");
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerUseYijkiStaff(PlayerInteractEvent e) throws Exception {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && this.isYijkiStaff(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            Player p = e.getPlayer();
            if (p.hasMetadata("lastYijkiTrinketUse") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastYijkiTrinketUse").get(0)).asLong() < 120000L) {
                p.sendMessage((Object)ChatColor.RED + "You cannot use Staff of Yijki for another " + (Object)ChatColor.GRAY + (120000L - System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastYijkiTrinketUse").get(0)).asLong()) / 1000L + "s");
                return;
            }
            if (!WorldGuardUtils.isPvPEnabled(p.getLocation())) {
                p.sendMessage((Object)ChatColor.RED + "You cannot use Staff of Yijki in a PvP-disabled zone.");
                return;
            }
            if (!KillTrackerAPI.hasSoulAmount((Player)p, (int)375)) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 375)" + (Object)ChatColor.BOLD + " ***");
                p.updateInventory();
                return;
            }
            KillTrackerAPI.removeSoulsFromGems((Player)p, (int)375);
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.7f, 1.2f);
            p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " -375 SOULS (" + (Object)ChatColor.WHITE + KillTrackerAPI.getAllSouls((Player)p) + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ")");
            ((MotherOfYijki)ArmorSet.MOTHER_OF_YIJKI.getArmorSetInfo()).activateYijki(p.getLocation(), p);
            p.setMetadata("lastYijkiTrinketUse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)System.currentTimeMillis()));
            p.updateInventory();
        }
    }

    private boolean isYijkiStaff(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("yijkiStaff");
    }
}

