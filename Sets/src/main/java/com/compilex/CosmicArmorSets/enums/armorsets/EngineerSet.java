/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EssentialsUtils
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.GeneralUtil
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicbosses.CosmicBosses
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityFallingBlock
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.World
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.Enchantments
 *  org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCustomEnchantment
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.*;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCustomEnchantment;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class EngineerSet
        extends ArmorSetInfo
        implements Listener,
        WeaponedArmorSet,
        OtherPlayerEventListenerSet {
    public static HashMap<org.bukkit.entity.Entity, Player> blocksAndProccer = Maps.newHashMap();
    private final ChatColor color = ChatColor.GOLD;
    private final Set<FallingBlock> fallingBlocks = Collections.newSetFromMap(new WeakHashMap());

    public EngineerSet() {
        Bukkit.getPluginManager().registerEvents(this, Enchantments.getInstance());
    }

    @Override
    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_HELMET, "Engineer Face Mask", "helmet");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_CHESTPLATE, "Engineer Explosion-Proof Vest", "chestplate");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_LEGGINGS, "Engineer Protective Chaps", "leggings");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack addEnchants(ItemStack item, List<String> enchants, double heroic) {
        for (String cen : enchants) {
            CustomEnchantment ce = EnchantmentAPI.getEnchantment(cen);
            if (ce == null) continue;
            if (ce instanceof HeroicCustomEnchantment) {
                if (heroic > 0.0 && Math.random() > heroic) continue;
                ce.addToItem(item, ce.getMaxLevel());
                ce.addToItem(item, 1 + new Random().nextInt(ce.getMaxLevel()));
                continue;
            }
            ce.addToItem(item, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + this.random.nextInt(3)));
        }
        return item;
    }

    public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
        if (enchantment == null) {
            return item;
        }
        enchantment.addToItem(item, enchantment.getMaxLevel());
        return item;
    }

    public ItemStack addRandomLeveledEnchant(String enchantName, ItemStack item) {
        CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(enchantName);
        if (enchantment == null) {
            return item;
        }
        enchantment.addToItem(item, (enchantment.getMaxLevel() > 1 ? this.random.nextInt(enchantment.getMaxLevel()) : 0) + 1);
        return item;
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack kothItem = this.createArmor(Material.DIAMOND_BOOTS, "Engineer Steel Toed Boots", "boots");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList(CC.GoldB + "ENGINEER SET BONUS:", CC.GoldB + " * " + CC.Gold + "Enemies deal 35% less damage to you.", CC.GoldB + " * " + CC.Gold + "Invisible on /near (Coming Soon)", CC.GoldB + " * " + CC.Gold + "Immune to Teleblock", CC.GoldB + " * " + CC.Gold + "Gears IV", CC.GoldB + " * " + CC.Gold + "B-2 Bomber Passive ability");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (Math.random() <= 0.09) {
            this.onEngineerAbility(damaged);
        }
        return true;
    }

    public void onEngineerAbility(Player player) {
        List<Player> affected;
        List<Player> players = affected = player.getNearbyEntities(25.0, 25.0, 25.0).stream().filter(ent -> ent instanceof Player).filter(e -> !GeneralUtil.isAtleastTruce(player, e)).filter(e -> ((Player) e).getGameMode() != GameMode.CREATIVE).map(e -> (Player) e).collect(Collectors.toList());
        player.sendMessage(CC.translate("&a&l* B-2 BOMBER DEPLOYED [&7" + affected.size() + " &7players&a&l] *"));
        for (Player enemy : affected) {
            enemy.sendMessage(CC.translate("&6&l* B-2 BOMB, RUN! &7(" + player.getName() + ") &6&l*"));
            this.fallingBlocks.add(this.createFallingBlock(enemy.getLocation().add(0.0, 5.0, 0.0), player, FactionUtils.getFaction(player)));
        }
    }

    private FallingBlock createFallingBlock(Location location, final Player original, final Faction faction) {
        final Material mat = Material.TNT;
        EntityFallingBlock block = new EntityFallingBlock((World) ((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), Block.getById(mat.getId()), 0) {
            boolean damagedPlayed;

            // TODO: BOWP spigot numbers
            {
                super(x0, x1, x2, x3, x4, x5);
                this.damagedPlayed = false;
            }

            public void die() {
                if (this.isAlive() && this.damagedPlayed) {
                    this.world.getWorld().playEffect(this.getBukkitEntity().getLocation(), Effect.STEP_SOUND, mat.getId());
                }
                ParticleEffect.playEffect(ParticleEffect.CLOUD, this.getBukkitEntity().getLocation(), 0.6f, 5);
                this.bukkitEntity.getLocation().getWorld().playSound(this.bukkitEntity.getLocation(), Sound.ZOMBIE_WOOD, 1.2f, 0.9f);
                super.die();
            }

            public void h() {
                if (this.ticksLived >= 1) {
                    try {
                        List<Entity> arraylist = this.world.getEntities(this, this.getBoundingBox());
                        arraylist.forEach(entity -> {
                            if (!(entity instanceof HumanEntity)) {
                                arraylist.remove(entity);
                            }
                        });

                        int killed = 0;
                        for (Entity ent : arraylist) {
                            CraftPlayer player;
                            if (!this.isAlive()) break;
                            if (!(ent instanceof EntityHuman) || (player = (CraftPlayer) ent.getBukkitEntity()).getGameMode() == GameMode.CREATIVE || player.equals(original) || player.isDead() || EssentialsUtils.isInGodMode(player) || player.hasMetadata("spectator") || player.hasMetadata("NPC") || player.getHealth() <= 0.0 || !WorldGuardUtils.isPvPEnabled(player.getLocation()) || faction.isNormal() && faction.getRelationTo(FPlayers.i.get(player)).isAtLeast(Relation.TRUCE))
                                continue;
                            if (player.hasMetadata("b2-bombed")) {
                                this.die();
                                continue;
                            }
                            player.setWalkSpeed(0.0f);
                            player.sendMessage(CC.translate("&c&l* B-2 BOMBED [&7" + original.getName() + " (Frozen for 3s)&c&l] *"));
                            player.setMetadata("b2-bombed", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), true));
                            Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicArmorSetsPlugin.get_(), () -> {
                                if (player.isOnline()) {
                                    EngineerSet.this.unfreeze(player);
                                }
                            }, 60L);
                            this.damagedPlayed = true;
                            ++killed;
                        }
                        if (killed > 0) {
                            this.die();
                            Bukkit.getLogger().info("Damaging " + killed + " players damage from falling block!");
                            return;
                        }
                        if (this.ticksLived % 20 == 0 && (this.world.getType(new BlockPosition(this.locX, this.locY, this.locZ)) == Blocks.WEB || this.world.getType(new BlockPosition(this.locX, this.locY - 1, this.locZ)) == Blocks.WEB)) {
                            this.die();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.h();
                if (this.onGround && this.isAlive()) {
                    this.die();
                }
            }
        };
        block.ticksLived = 1;
        ((CraftWorld) location.getWorld()).getHandle().addEntity(block, CreatureSpawnEvent.SpawnReason.CUSTOM);
        FallingBlock fallingBlock = (FallingBlock) block.getBukkitEntity();
        fallingBlock.setMetadata("b2Bomber", new FixedMetadataValue(CosmicBosses.getInstance(), faction.getId()));
        fallingBlock.setDropItem(false);
        return fallingBlock;
    }

    @Override
    public void onPlayerEvent(Event event) {
        if (event instanceof EntityChangeBlockEvent) {
            org.bukkit.entity.Entity falling = ((EntityChangeBlockEvent) event).getEntity();
            if (this.fallingBlocks.remove(falling)) {
                ((EntityChangeBlockEvent) event).setCancelled(true);
                falling.remove();
            }
        } else if (event instanceof PlayerQuitEvent) {
            this.unfreeze(((PlayerQuitEvent) event).getPlayer());
        } else if (event instanceof PlayerTeleportEvent) {
            this.unfreeze(((PlayerTeleportEvent) event).getPlayer());
        }
    }

    private void unfreeze(Player player) {
        player.removeMetadata("b2-bombed", CosmicArmorSetsPlugin.get_());
        player.setWalkSpeed(0.2f);
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        Material type;
        ItemStack handItem = damager.getItemInHand();
        double chance = 4.0;
        if (handItem != null && (type = handItem.getType()) != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && new NBTWrapper(handItem).getString("cosmicType").equals("ENGINEER_SWORD")) {
            chance *= 1.2;
        }
        if (ThreadLocalRandom.current().nextDouble(200.0) <= chance && Math.random() < 0.05) {
            this.onEngineerAbility(damager);
        }
        return false;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return false;
        }
        if (damaged.getWorld().getName().contains("dungeon")) {
            return false;
        }
        if ((double) ThreadLocalRandom.current().nextInt(100) <= 4.7 * (double) multiplier) {
            this.onEngineerAbility(damaged);
        }
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        return false;
    }

    public ItemStack createArmor(Material material, String itemName, String loreType) {
        String bold = this.color + CC.Bold + "* " + this.color;
        ItemStack armor = ItemUtils.buildItem(material, (short) 0, this.color + CC.Bold + itemName + CC.Reset, this.color + CC.Italic + "Next-gen Gnometech " + loreType + " for demolition specialists", "", this.color + CC.Bold + "ENGINEER SET BONUS", bold + "Enemies deal 35% less damage to you.", bold + "Invisible on /near", bold + "Immune to Teleblock", bold + "Gears IV", bold + "B-2 Bomber Passive Ability", CC.Gray + "(" + CC.GrayI + "Requires all 4 engineer set items" + CC.Gray + ")");
        armor.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        armor.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        return armor;
    }

    @Override
    public ItemStack getWeapon(boolean p0) {
        return new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD, CC.GoldB + "Engineer Hammer", CC.Gray + "A titan-forged grandmaster", CC.Gray + "warhammer that crushes all.", "", CC.GoldB + "ENGINEER WEAPON BONUS", CC.Gold + "Deal +15% damage to all enemies.", CC.Gold + "120% B-2 Bomber Ability", CC.Gold + "Immune to World Destroyer Pet", CC.Gold + "Immune to Shockwave, Obliterate, Guardians", CC.Gray + "(" + CC.GrayI + "Requires all 4 engineer items." + CC.Gray + ")").enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "ENGINEER_SWORD").build();
    }
}

