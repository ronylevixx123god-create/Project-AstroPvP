/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems
 *  com.cosmicpvp.CosmicSpaceChests.libs.KitItems
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.cosmicpvp.CosmicGlobalKits.listeners;

import com.cosmicpvp.CosmicSpaceChests.libs.EvolutionKitItems;
import com.cosmicpvp.CosmicSpaceChests.libs.KitItems;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.FallenHero;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.cosmicpvp.CosmicGlobalKits.utils.WorldGuardUtils;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FallenHeroListener
implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) throws Exception {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && FallenHero.isFallenHeroItem(e.getItem())) {
            Player pNear;
            String itemName;
            LivingEntity fallenHero;
            Location l;
            ItemStack i;
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            Player p = e.getPlayer();
            FLocation fl = new FLocation(p.getLocation());
            if (!Board.getFactionAt((FLocation)fl).isWarZone()) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot summon a Fallen Hero outside the Warzone.");
                return;
            }
            if (com.cosmicpvp.cosmicutils.utils.WorldGuardUtils.isInRegionsContainsFast((Location)p.getLocation(), (List)Lists.newArrayList((Object[])new String[]{"sell_room"}))) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot summon a Fallen Hero at this location.");
                return;
            }
            if (WorldGuardUtils.isPvPDisabled(p.getLocation())) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot summon a Fallen Hero inside a Safezone.");
                return;
            }
            if (p.getWorld().getEnvironment() == World.Environment.NETHER && p.getLocation().getY() >= 128.0) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot summon a Fallen Hero at this location.");
                return;
            }
            if (p.getWorld().getName().contains("world_duels")) {
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot summon a fallen hero inside a duel!");
                return;
            }
            GlobalKits gkit = FallenHero.getGlobalKitFromFallenHeroItem(e.getItem());
            EvolutionKits vkit = FallenHero.getEvolutionKitFromFallenHeroItem(e.getItem());
            if (gkit != null) {
                i = e.getItem();
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    p.setItemInHand(i);
                } else {
                    p.setItemInHand(null);
                }
                l = p.getTargetBlock((HashSet<Byte>) null, 6).getLocation().add(0.0, 1.0, 0.0);
                fallenHero = FallenHero.spawnFallenHero(gkit, l);
                fallenHero.setMetadata("fallenHeroSummoner", (MetadataValue)new FixedMetadataValue((Plugin)CosmicGlobalKits.getPlugin(), (Object)p.getName()));
                itemName = gkit.getItemName().replace(" Kit", "");
                p.sendMessage("");
                p.sendMessage((Object)ChatColor.WHITE + "" + (Object)ChatColor.BOLD + "*** " + (Object)ChatColor.UNDERLINE + "Fallen Hero:" + (Object)ChatColor.RESET + " " + itemName + (Object)ChatColor.WHITE + "" + (Object)ChatColor.BOLD + " ***");
                p.sendMessage((Object)ChatColor.GRAY + "You have summoned a Fallen Hero! Defeat it to claim its treasures and a chance to " + (Object)ChatColor.UNDERLINE + "unlock its /gkit!");
                p.sendMessage("");
                for (Entity ent : p.getNearbyEntities(32.0, 32.0, 32.0)) {
                    if (!(ent instanceof Player)) continue;
                    pNear = (Player)ent;
                    pNear.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + p.getName() + " has summoned a " + itemName + (Object)ChatColor.YELLOW + " Fallen Hero!");
                    pNear.sendMessage((Object)ChatColor.GRAY + "Locate and slay the Fallen Hero to steal its loot for yourself!");
                }
                p.playSound(p.getLocation(), Sound.WITHER_SPAWN, 1.0f, 1.2f);
                de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), fallenHero.getLocation(), 0f, 0.5f, 0f, 0.8f, 35);
                de.inventivegames.particle.ParticleEffect.FIREWORKS_SPARK.sendToPlayers(Bukkit.getOnlinePlayers(), fallenHero.getLocation(), 0f, 0.5f, 0f, 0.8f, 50);
            }
            if (vkit != null) {
                i = e.getItem();
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    p.setItemInHand(i);
                } else {
                    p.setItemInHand(null);
                }
                l = p.getTargetBlock((HashSet<Byte>) null, 6).getLocation().add(0.0, 1.0, 0.0);
                fallenHero = FallenHero.spawnFallenHero(vkit, l);
                fallenHero.setMetadata("fallenHeroSummoner", (MetadataValue)new FixedMetadataValue((Plugin)CosmicGlobalKits.getPlugin(), (Object)p.getName()));
                itemName = vkit.getKit().getItemName();
                p.sendMessage("");
                p.sendMessage((Object)ChatColor.WHITE + "" + (Object)ChatColor.BOLD + "*** " + (Object)ChatColor.UNDERLINE + "Fallen Hero:" + (Object)ChatColor.RESET + " " + itemName + (Object)ChatColor.WHITE + "" + (Object)ChatColor.BOLD + " ***");
                p.sendMessage((Object)ChatColor.GRAY + "You have summoned a Fallen Hero! Defeat it to claim its treasures and a chance to " + (Object)ChatColor.UNDERLINE + "unlock its /vkit!");
                p.sendMessage("");
                for (Entity ent : p.getNearbyEntities(32.0, 32.0, 32.0)) {
                    if (!(ent instanceof Player)) continue;
                    pNear = (Player)ent;
                    pNear.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + p.getName() + " has summoned a " + itemName + (Object)ChatColor.YELLOW + " Fallen Hero!");
                    pNear.sendMessage((Object)ChatColor.GRAY + "Locate and slay the Fallen Hero to steal its loot for yourself!");
                }
                p.playSound(p.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 1.0f, 1.2f);
                de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), fallenHero.getLocation(), 0f, 0.0f, 0f, 0.8f, 35);
                de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), fallenHero.getLocation(), 0f, 0.0f, 0f, 0.8f, 35);

            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("fallenHero") && !e.getEntity().hasMetadata("hasDied")) {
            LivingEntity ent = e.getEntity();
            String internalName = e.getEntity().hasMetadata("fallenHeroName") ? ((MetadataValue)e.getEntity().getMetadata("fallenHeroName").get(0)).asString() : null;
            GlobalKits gkit = null;
            try {
                gkit = GlobalKits.valueOf(((MetadataValue)ent.getMetadata("fallenHero").get(0)).asString());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            EvolutionKits vkit = null;
            try {
                vkit = EvolutionKits.valueOf((String)(internalName != null ? internalName : ((MetadataValue)ent.getMetadata("fallenHero").get(0)).asString()));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            e.getDrops().clear();
            if (gkit != null) {
                double kitDropChance;
                ent.setMetadata("hasDied", (MetadataValue)new FixedMetadataValue((Plugin)CosmicGlobalKits.getPlugin(), (Object)true));
                double d = kitDropChance = ent.hasMetadata("mysteryFallenHero") ? 0.5 : 0.22;
                if (Math.random() <= kitDropChance) {
                    this.createCustomItem(ent.getWorld().dropItem(ent.getLocation(), KitItems.getKitItem((GlobalKits)gkit)));
                    String playerName = ((MetadataValue)ent.getMetadata("fallenHeroSummoner").get(0)).asString();
                    Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GOLD + playerName + " has unlocked the " + gkit.getItemName() + (Object)ChatColor.GOLD + " from a Fallen Hero!"));
                } else {
                    for (ItemStack i : CosmicGlobalKitsAPI.getGlobalKitItems(gkit, null)) {
                        this.createCustomItem(ent.getWorld().dropItem(ent.getLocation(), i));
                    }
                }
                ent.remove();
            }
            if (vkit != null) {
                ent.setMetadata("hasDied", (MetadataValue)new FixedMetadataValue((Plugin)CosmicGlobalKits.getPlugin(), (Object)true));
                if (Math.random() <= 0.1) {
                    this.createCustomItem(ent.getWorld().dropItem(ent.getLocation(), vkit.getLevelUpItem(true)));
                    Bukkit.getLogger().info("Dropping Vkit Level up Crystal at " + (Object)ent.getLocation() + " vkit: " + vkit.name());
                }
                if (Math.random() <= 0.22) {
                    this.createCustomItem(ent.getWorld().dropItem(ent.getLocation(), EvolutionKitItems.getKitItem((EvolutionKits)vkit)));
                    String playerName = ((MetadataValue)ent.getMetadata("fallenHeroSummoner").get(0)).asString();
                    Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GOLD + playerName + " has unlocked the " + vkit.getKit().getItemName() + (Object)ChatColor.GOLD + " /vkit from a Fallen Hero!"));
                } else {
                    LivingEntity le = ent;
                    Player p = le.getKiller();
                    if (p == null) {
                        for (Entity eNear : le.getNearbyEntities(16.0, 16.0, 16.0)) {
                            if (!(eNear instanceof Player)) continue;
                            p = (Player)eNear;
                            break;
                        }
                    }
                    if (p != null) {
                        for (ItemStack i : vkit.getKit().getItems(p, 1 + ThreadLocalRandom.current().nextInt(4))) {
                            this.createCustomItem(ent.getWorld().dropItem(ent.getLocation(), i));
                        }
                    }
                }
                ent.remove();
            }
        }
    }

    private Item createCustomItem(Item item) {
        item.setMetadata("noItemDamage", (MetadataValue)new FixedMetadataValue((Plugin)CosmicGlobalKits.getPlugin(), (Object)true));
        item.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicGlobalKits.getPlugin(), (Object)true));
        return item;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("noItemDamage")) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }
}

