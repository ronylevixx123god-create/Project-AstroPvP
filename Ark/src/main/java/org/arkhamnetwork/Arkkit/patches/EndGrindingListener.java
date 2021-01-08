/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicCustomCreepers.enums.CreeperTypes
 *  com.compilex.CosmicDungeons.DungeonsAPI
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.cosmicpvp.CosmicSpaceChests.generators.ArmorGenerator
 *  com.cosmicpvp.CosmicSpaceChests.generators.WeaponGenerator
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World$Environment
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.CosmicSpaceChests.generators.ArmorGenerator;
import com.cosmicpvp.CosmicSpaceChests.generators.WeaponGenerator;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.CosmicCustomTNT.enums.TNTTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

final class EndGrindingListener
        implements Listener {
    private final HashMap<ItemStack, Double> potionDrops;
    private final LinkedHashMap<Integer, Double> killThresholds;
    private final DecimalFormat format = new DecimalFormat("#,###");

    EndGrindingListener() {
        this.potionDrops = new HashMap();
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8257), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8289), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8259), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8297), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8290), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8269), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8289), 0.02);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8291), 0.02);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8297), 0.02);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 8290), 0.02);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16449), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16481), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16451), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16489), 0.1);
        this.potionDrops.put(new ItemStack(Material.POTION, 1, (short) 16482), 0.1);
        this.killThresholds = new LinkedHashMap();
        this.killThresholds.put(5, 1.1);
        this.killThresholds.put(10, 1.15);
        this.killThresholds.put(25, 1.25);
        this.killThresholds.put(50, 1.5);
        this.killThresholds.put(75, 1.75);
        this.killThresholds.put(100, 2.0);
        this.killThresholds.put(150, 2.5);
        this.killThresholds.put(200, 3.0);
        this.killThresholds.put(300, 4.0);
        this.killThresholds.put(500, 5.0);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEndMonsterDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("endMonsterType")) {
            Player killer;
            EndMonsterType type = EndMonsterType.valueOf(e.getEntity().getMetadata("endMonsterType").get(0).asString());
            if (type == EndMonsterType.CREEPER) {
                if (Math.random() < 0.05) {
                    double eggRand = Math.random();
                    if (eggRand < 0.5) {
                        e.getDrops().add(new ItemStack(Material.MONSTER_EGG, 1, (short) 50));
                    } else if (eggRand < 0.75) {
                        e.getDrops().add(ItemUtils.getCustomCreeperEgg(CreeperTypes.Tactical));
                    } else if (eggRand < 0.9) {
                        e.getDrops().add(ItemUtils.getCustomCreeperEgg(CreeperTypes.Huge));
                    } else {
                        e.getDrops().add(ItemUtils.getCustomCreeperEgg(CreeperTypes.Lucky));
                    }
                }
                if (Math.random() < 0.2) {
                    e.getDrops().add(TNTTypes.getRandomCustomTNT(4 + new Random().nextInt(3)));
                }
            } else if (type == EndMonsterType.CUSTOM) {
                double moneyRand;
                ItemStack item;
                e.getDrops().clear();
                if (Math.random() < 0.2) {
                    for (Map.Entry<ItemStack, Double> data : this.potionDrops.entrySet()) {
                        if (!(Math.random() < data.getValue())) continue;
                        e.getDrops().add(data.getKey().clone());
                    }
                }
                if (Math.random() < 0.1) {
                    if (Math.random() < 0.6) {
                        e.getDrops().add(ArmorGenerator.getRandomKitArmor());
                    } else {
                        e.getDrops().add(WeaponGenerator.getRandomKitWeapon());
                    }
                }
                if (Math.random() < 0.15) {
                    e.getDrops().add(CosmicConquestAPI.createFactionToken(1 + new Random().nextInt(2)));
                }
                if (Math.random() < 0.25) {
                    e.getDrops().add(this.getRandomEnchantedVanillaArmor());
                }
                if (Math.random() < 0.0025) {
                    e.getDrops().add(DungeonsAPI.get().getEnchantedObsidian());
                }
                if (Math.random() < 0.001) {
                    e.getDrops().add(DungeonsAPI.get().getDimensionWeb());
                }
                if (Math.random() <= 0.01 && (item = FactionPointUtils.getFactionPointsItem(null, null, "end_custom", e)) != null) {
                    e.getDrops().add(item);
                }
                if (Math.random() < 0.001) {
                    if (Math.random() < 0.1) {
                        e.getDrops().add(ItemUtils.getTitleVoucher("#End"));
                    } else {
                        e.getDrops().add(ItemUtils.getTitleVoucher("Toxic"));
                    }
                }
                if ((moneyRand = Math.random()) < 0.05) {
                    e.getDrops().add(ItemUtils.getMoneyNote("End Dimension", 10000 + new Random().nextInt(25000)));
                } else if (moneyRand < 0.25) {
                    e.getDrops().add(ItemUtils.getMoneyNote("End Dimension", 5000 + new Random().nextInt(20000)));
                } else if (moneyRand < 0.5) {
                    e.getDrops().add(ItemUtils.getMoneyNote("End Dimension", 2500 + new Random().nextInt(10000)));
                } else {
                    e.getDrops().add(ItemUtils.getMoneyNote("End Dimension", 1000 + new Random().nextInt(5000)));
                }
                Player pKiller = e.getEntity().getKiller();
                if (pKiller != null) {
                    int killstreak = pKiller.hasMetadata("endMonsterKills") ? pKiller.getMetadata("endMonsterKills").get(0).asInt() : 0;
                    double killstreakMultiPerk = FactionUpgradeAPI.getPerkLevel(pKiller, FactionUpgrade.ENDER_FARMING);
                    int killstreakAdd = 1;
                    if (killstreakMultiPerk == 1.0) {
                        killstreakAdd *= 2;
                    }
                    if (killstreakMultiPerk == 2.0) {
                        killstreakAdd *= 3;
                    }
                    if (killstreakMultiPerk == 3.0) {
                        killstreakAdd *= 5;
                    }
                    double killXPMod = this.getKillstreakXPMultiplier(killstreak += killstreakAdd);
                    e.getDrops().add(ItemUtils.getXpBottle((int) (1000.0 * killXPMod), "End Dimension [" + killXPMod + "x]"));
                    pKiller.setMetadata("endMonsterKills", new FixedMetadataValue(Arkkit.get(), killstreak));
                    if (killstreak == 1) {
                        pKiller.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "(!) " + ChatColor.LIGHT_PURPLE + "You have killed an Ender Monster! Kill more without dieing or leaving the End to unlock XP multipliers to the XP Bottles they drop!");
                        pKiller.sendMessage(ChatColor.GRAY + "The first threshold is at 5 kills, and at 500 kills you get " + ChatColor.UNDERLINE + "5x XP!");
                    } else {
                        pKiller.sendMessage(ChatColor.LIGHT_PURPLE + "Ender Monster Kills: " + ChatColor.BOLD + killstreak);
                    }
                    if (this.killThresholds.containsKey(killstreak)) {
                        pKiller.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "(!) " + killstreak + " Ender Monster Kills! " + ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + (int) (killXPMod * 100.0) + "% XP" + ChatColor.GRAY + "]");
                        if (killstreak >= 25) {
                            Faction f = FPlayers.i.get(pKiller).getFaction();
                            for (Player p : killstreak >= 50 ? Bukkit.getOnlinePlayers() : pKiller.getWorld().getPlayers()) {
                                if (p.equals(pKiller)) continue;
                                p.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "(!) " + ChatColor.LIGHT_PURPLE + ChatColor.UNDERLINE + (f.isNormal() ? f.getTag() + " " : "") + pKiller.getName() + ChatColor.DARK_PURPLE + "(" + ChatColor.WHITE + "/warp end, " + pKiller.getLocation().getBlockX() + "x, " + pKiller.getLocation().getBlockY() + "y, " + pKiller.getLocation().getBlockZ() + "z" + ChatColor.DARK_PURPLE + ") has killed " + killstreak + "x Ender Monsters! They have grinded ~" + ChatColor.UNDERLINE + this.format.format((int) (killXPMod * 1000.0) * killstreak) + "XP" + ChatColor.DARK_PURPLE + " can anyone stop them?");
                            }
                        }
                    }
                }
            }
            if ((killer = e.getEntity().getKiller()) != null && CosmicUtils.get().isOutpostsEnabled() && CosmicOutpostsAPI.controlsHeroOutpost(killer)) {
                for (ItemStack item : e.getDrops()) {
                    if (item == null || item.getType() == Material.EXP_BOTTLE) continue;
                    ItemStack bonusItem = item.clone();
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        ItemID.setItemID(bonusItem, ItemID.generateNewItemID());
                    }
                    e.getDrops().add(bonusItem);
                }
            }
        }
    }

    @EventHandler
    public void onCreeperPrime(ExplosionPrimeEvent e) {
        if (e.getEntity() instanceof Creeper && e.getEntity().hasMetadata("endMonsterType")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().hasMetadata("endMonsterKills")) {
            e.getEntity().removeMetadata("endMonsterKills", Arkkit.get());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.getPlayer().removeMetadata("endMonsterKills", Arkkit.get());
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getFrom().getWorld().getName().equals("world_end") && !e.getTo().getWorld().equals(e.getFrom().getWorld())) {
            e.getPlayer().removeMetadata("endMonsterKills", Arkkit.get());
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        if (e.getWorld().getEnvironment() == World.Environment.THE_END) {
            for (Entity ent : e.getChunk().getEntities()) {
                if (!ent.hasMetadata("endMonsterType")) continue;
                ent.remove();
            }
        }
    }

    private double getKillstreakXPMultiplier(int killstreak) {
        double highest = 1.0;
        for (Map.Entry<Integer, Double> checks : this.killThresholds.entrySet()) {
            if (killstreak >= checks.getKey() && checks.getValue() > highest) {
                highest = checks.getValue();
                continue;
            }
            if (killstreak >= checks.getKey()) continue;
            break;
        }
        return highest;
    }

    private ItemStack getRandomEnchantedVanillaArmor() {
        double d = new Random().nextInt(4);
        ItemStack i = null;
        if (d == 0.0) {
            i = new ItemStack(Material.DIAMOND_HELMET);
        }
        if (d == 1.0) {
            i = new ItemStack(Material.DIAMOND_CHESTPLATE);
        }
        if (d == 2.0) {
            i = new ItemStack(Material.DIAMOND_LEGGINGS);
        }
        if (d == 3.0) {
            i = new ItemStack(Material.DIAMOND_BOOTS);
        }
        i.addEnchantment(Enchantment.DURABILITY, 3);
        i.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Math.random() < 0.5 ? 4 : 3);
        return i;
    }
}

