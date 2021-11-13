/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicArmorSets.enums.armorsets.MotherOfYijki
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  me.quantiom.util.ItemSkinUtils
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown
 *  org.arkhamnetwork.enchantments.Enchantments
 *  org.arkhamnetwork.enchantments.enchantments.armor.Phoenix
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PrePlayerDeathEvent
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
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.struct.DungeonDebuff;
import com.compilex.CosmicDungeons.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.quantiom.util.ItemSkinUtils;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PrePlayerDeathEvent;
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
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && SoulItemListener.isSoulPearl(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            if (DungeonsAPI.hasDungeonDebuff(e.getPlayer(), DungeonDebuff.NO_ENDER_PEARLS)) {
                return;
            }
            Player p = e.getPlayer();
            if (!EnderpearlCooldown.isEnderpearlCooldownOver((Player)p) || !SoulItemListener.canThrowPearl(e.getItem(), p)) {
                return;
            }
            if (KillTrackerAPI.hasSoulAmount((Player)p, (int)5)) {
                KillTrackerAPI.removeSoulsFromGems((Player)p, (int)5);
                e.getPlayer().sendMessage(CC.translate((String)"&c&l- 5 Souls &7(Soul Pearl)"));
                p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.67f);
                if (!ItemSkinUtils.hasItemSkin((Player)p, (String)"SEXY_BELT")) {
                    p.launchProjectile(EnderPearl.class);
                }
                p.updateInventory();
            } else {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 5)" + (Object)ChatColor.BOLD + " ***");
                p.updateInventory();
                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                return;
            }
        }
    }

    public static boolean isSoulPearl(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_PEARL && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().equals(DungeonsRewardsAPI.getSoulPearl().getItemMeta().getLore());
    }

    public static boolean isOmniPearl(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_PEARL && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().equals(DungeonsRewardsAPI.getOmniPearl().getItemMeta().getLore());
    }

    public static boolean canThrowPearl(ItemStack item, Player pl) {
        if (pl.hasMetadata("lastEnderpearlThrow")) {
            long lastEnderpearlThrow = ((MetadataValue)pl.getMetadata("lastEnderpearlThrow").get(0)).asLong();
            long cd = 400L;
            long timeSinceThrow = System.currentTimeMillis() - lastEnderpearlThrow;
            if (timeSinceThrow < cd) {
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
            if (KillTrackerAPI.hasSoulAmount((Player)p, (int)100)) {
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
            } else {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 100)" + (Object)ChatColor.BOLD + " ***");
                return;
            }
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
            if (KillTrackerAPI.hasSoulAmount((Player)p, (int)100)) {
                KillTrackerAPI.removeSoulsFromGems((Player)p, (int)100);
                p.removePotionEffect(PotionEffectType.SPEED);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 140, 3));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.7f, 1.2f);
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " -100 SOULS (" + (Object)ChatColor.WHITE + KillTrackerAPI.getAllSouls((Player)p) + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ")");
                p.sendMessage((Object)ChatColor.DARK_AQUA + "" + (Object)ChatColor.BOLD + "* SPEED IV (7s) *");
                p.setMetadata("lastSpeedTrinketUse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)System.currentTimeMillis()));
                p.updateInventory();
            } else {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 100)" + (Object)ChatColor.BOLD + " ***");
                p.updateInventory();
                return;
            }
        }
    }

    private boolean isSpeedTrinket(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("speedTrinket");
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerDamaged(PrePlayerDeathEvent e) {
        boolean bypass;
        Player p = e.getPlayer();
        boolean hasPhoenix = Phoenix.hasPhoenixEquipped((Player)p);
        int phoenixLevel = Phoenix.phoenixEquipped.getOrDefault(p.getUniqueId(), 0);
        if (hasPhoenix && KillTrackerAPI.hasSoulAmount((Player)p, (int)Phoenix.getPhoenixProcSoulCost((Player)p)) && Phoenix.canUsePhoenix((Player)p, (int)phoenixLevel)) {
            return;
        }
        ItemStack phoenixFeather = null;
        for (Object i : p.getInventory()) {
            if (!this.isPhoenixFeather((ItemStack)i)) continue;
            phoenixFeather = i;
            break;
        }
        boolean bl = bypass = ItemSkinUtils.hasItemSkin((Player)p, (String)"ROSE_AMULET") && (!p.hasMetadata("roseAmulet") || ((MetadataValue)p.getMetadata("roseAmulet").get(0)).asLong() - System.currentTimeMillis() <= -1800000L);
        if (Enchantments.getInstance().isDebugging()) {
            System.out.println("Player has BYPASS Amulet equipped (" + p.getName() + ")!");
        }
        if (phoenixFeather != null || bypass) {
            if (!bypass) {
                p.getInventory().remove(phoenixFeather);
            } else {
                p.setMetadata("roseAmulet", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)System.currentTimeMillis()));
                p.sendMessage(ChatColor.RED.toString() + "** ROSE AMULET **");
            }
            p.setHealth(p.getMaxHealth());
            e.setCancelled(true);
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
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.FLAME, (Location)loc, (float)((float)Math.random()), (float)((float)Math.random()), (float)((float)Math.random()), (float)0.75f, (int)80);
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LAVA, (Location)loc, (float)((float)Math.random()), (float)((float)Math.random()), (float)((float)Math.random()), (float)0.7f, (int)20);
        }
    }

    private boolean isPhoenixFeather(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("phoenixFeather");
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerUseYijkiStaff(PlayerInteractEvent e) {
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
            if (KillTrackerAPI.hasSoulAmount((Player)p, (int)375)) {
                KillTrackerAPI.removeSoulsFromGems((Player)p, (int)375);
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 0.7f, 1.2f);
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " -375 SOULS (" + (Object)ChatColor.WHITE + KillTrackerAPI.getAllSouls((Player)p) + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ")");
                ((MotherOfYijki)ArmorSet.MOTHER_OF_YIJKI.getArmorSetInfo()).activateYijki(p.getLocation(), p);
                p.setMetadata("lastYijkiTrinketUse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)System.currentTimeMillis()));
                p.updateInventory();
            } else {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 375)" + (Object)ChatColor.BOLD + " ***");
                p.updateInventory();
                return;
            }
        }
    }

    private boolean isYijkiStaff(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("yijkiStaff");
    }
}

