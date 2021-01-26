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
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.*;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
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

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class EngineerSet extends ArmorSetInfo implements Listener, WeaponedArmorSet, OtherPlayerEventListenerSet {
    public static HashMap<Entity, Player> blocksAndProccer = Maps.newHashMap();
    private final ChatColor color;
    private final Set<FallingBlock> fallingBlocks = Collections.newSetFromMap(new WeakHashMap<>());

    public EngineerSet() {
        this.color = ChatColor.GOLD;
        Bukkit.getPluginManager().registerEvents(this, Enchantments.getInstance());
    }

    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }

    public ItemStack getHelmet(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_HELMET, "Engineer Face Mask", "helmet");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_CHESTPLATE, "Engineer Explosion-Proof Vest", "chestplate");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_LEGGINGS, "Engineer Protective Chaps", "leggings");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack addEnchants(ItemStack item, List<String> enchants, double heroic) {
        for (String cen : enchants) {
            CustomEnchantment ce = EnchantmentAPI.getEnchantment(cen);
            if (ce == null)
                continue;
            if (ce instanceof org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCustomEnchantment) {
                if (heroic > 0.0D && Math.random() > heroic)
                    continue;
                ce.addToItem(item, ce.getMaxLevel());
                ce.addToItem(item, 1 + (new Random()).nextInt(ce.getMaxLevel()));
                continue;
            }
            ce.addToItem(item, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + this.random.nextInt(3)));
        }
        return item;
    }

    public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
        if (enchantment == null)
            return item;
        enchantment.addToItem(item, enchantment.getMaxLevel());
        return item;
    }

    public ItemStack addRandomLeveledEnchant(String enchantName, ItemStack item) {
        CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(enchantName);
        if (enchantment == null)
            return item;
        enchantment.addToItem(item, ((enchantment.getMaxLevel() > 1) ? this.random.nextInt(enchantment.getMaxLevel()) : 0) + 1);
        return item;
    }

    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack kothItem = createArmor(Material.DIAMOND_BOOTS, "Engineer Steel Toed Boots", "boots");
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public List<String> getEquipDescription() {
        return Lists.newArrayList(CC.GoldB + "ENGINEER SET BONUS:", CC.GoldB + " * " + CC.Gold + "Enemies deal 35% less damage to you.", CC.GoldB + " * " + CC.Gold + "Invisible on /near (Coming Soon)", CC.GoldB + " * " + CC.Gold + "Immune to Teleblock", CC.GoldB + " * " + CC.Gold + "Gears IV", CC.GoldB + " * " + CC.Gold + "B-2 Bomber Passive ability");
    }

    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (Math.random() <= 0.09) {
            onEngineerAbility(damaged);
        }
        //event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.65D);
        return true;
    }


    public void onEngineerAbility(Player player) {
        List<Player> affected = player.getNearbyEntities(25.0D, 25.0D, 25.0D).stream().filter(ent -> ent instanceof Player)
                .filter(e -> !GeneralUtil.isAtleastTruce(player, (Player) e)).filter(e -> ((Player) e).getGameMode() != GameMode.CREATIVE).map(e -> (Player) e).collect(Collectors.toList()), players = affected;
        player.sendMessage(CC.translate("&a&l* B-2 BOMBER DEPLOYED [&7" + affected.size() + " &7players&a&l] *"));
        for (Player enemy : affected) {
            enemy.sendMessage(CC.translate("&6&l* B-2 BOMB, RUN! &7(" + player.getName() + ") &6&l*"));
            this.fallingBlocks.add(createFallingBlock(enemy.getLocation().add(0, 5, 0), player,
                    FactionUtils.getFaction(player)));
        }
    }

    private FallingBlock createFallingBlock(Location location, final Player original, final Faction faction) {
        final Material mat = Material.TNT;
        EntityFallingBlock block = new EntityFallingBlock(location, (World) location.getWorld(), location.getX(), location.getY(), location.getZ(), Block.getById(mat.getId()).getBlockData()) {
            boolean damagedPlayed = false;


            @SneakyThrows
            public void die() {
                /* 296 */
                if (isAlive() && this.damagedPlayed) {

                    this.world.getWorld().playEffect(getBukkitEntity().getLocation(), Effect.STEP_SOUND, mat.getId());

                }
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), this.getBukkitEntity().getLocation(), 0f, 0.5f, 0f, 0.6f, 5);

                this.bukkitEntity.getLocation().getWorld().playSound(this.bukkitEntity.getLocation(), Sound.ZOMBIE_WOOD, 1.2F, 0.9f);

                super.die();

            }


            public void h() {
                /* 304 */
                if (this.ticksLived >= 1) {

                    try {
                        List<net.minecraft.server.v1_8_R3.Entity> arraylist = this.world.getEntities(this, this.getBoundingBox());
                        int killed = 0;
                        for (net.minecraft.server.v1_8_R3.Entity ent : arraylist) {
                            if (!isAlive())
                                break;
                            if (ent instanceof EntityHuman) {
                                CraftPlayer player = (CraftPlayer) ent.getBukkitEntity();
                                if (player.getGameMode() == GameMode.CREATIVE || player.equals(original) || player.isDead() ||
                                        EssentialsUtils.isInGodMode(player) || player.hasMetadata("spectator") || player.hasMetadata("NPC") || player.getHealth() <= 0.0D || !WorldGuardUtils.isPvPEnabled(player.getLocation())) {
                                    continue;
                                }

                                if (faction.isNormal() && faction.getRelationTo(FPlayers.i.get(player)).isAtLeast(Relation.TRUCE)) {
                                    continue;
                                }
                                /* 335 */

                                /* 337 */
                                if (player.hasMetadata("b2-bombed")) {

                                    /* 339 */
                                    die();


                                    continue;

                                }


                                player.setWalkSpeed(0.0f);
                                player.sendMessage(CC.translate("&c&l* B-2 BOMBED [&7" + original.getName() + " (Frozen for 3s)&c&l] *"));
                                player.setMetadata("b2-bombed", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), true));

                                Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicArmorSetsPlugin.get_(), () -> {

                                    if (player.isOnline()) unfreeze(player);

                                }, 3 * 20);
                                this.damagedPlayed = true;
                                killed++;
                            }
                        }
                        if (killed > 0) {
                            die();
                            Bukkit.getLogger().info("Damaging " + killed + " players damage from falling block!");
                            return;
                        }
                        if (this.ticksLived % 20 == 0) {
                            if (this.world.getType(new BlockPosition(this.locX, (int) this.locY, (int) this.locZ)) == Blocks.WEB || this.world
/* 361 */.getType(new BlockPosition(this.locX, (int) this.locY - 1, (int) this.locZ)) == Blocks.WEB) {
                                die();

                            }

                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
                super.h();
                if (this.onGround && isAlive()) {
                    die();
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

    public void onPlayerEvent(Event event) {
        /* 425 */
        if (event instanceof EntityChangeBlockEvent) {
            /* 426 */
            org.bukkit.entity.Entity falling = ((EntityChangeBlockEvent) event).getEntity();
            /* 427 */
            if (this.fallingBlocks.remove(falling)) {

                ((EntityChangeBlockEvent) event).setCancelled(true);
                /* 430 */
                falling.remove();

            }
            /* 432 */
        } else if (event instanceof PlayerQuitEvent) {
            /* 433 */
            unfreeze(((PlayerQuitEvent) event).getPlayer());
            /* 434 */
            /* 435 */
        } else if (event instanceof PlayerTeleportEvent) {
            /* 436 */
            unfreeze(((PlayerTeleportEvent) event).getPlayer());

        }
    }

    private void unfreeze(Player player) {
        player.removeMetadata("b2-bombed", CosmicArmorSetsPlugin.get_());
        player.setWalkSpeed(0.2f);
    }

    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        double chance = 4.0D;
        if (ThreadLocalRandom.current().nextDouble(200.0D) <= chance) {
            if (Math.random() < 0.05) {
                onEngineerAbility(damager);
            }
        }
        return false;
    }

    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return false;
        if (damaged.getWorld().getName().contains("dungeon"))
            return false;
        if ((ThreadLocalRandom.current().nextInt(100)) <= 4.7D * multiplier)
            onEngineerAbility(damaged);
        return false;
    }

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

    public ItemStack getWeapon(boolean p0) {
        return (new NBTWrapper((new ItemBuilder(Material.DIAMOND_SWORD, CC.GoldB + "Engineer Hammer", CC.Gray + "A titan-forged grandmaster", CC.Gray + "warhammer that crushes all.", "", CC.GoldB + "ENGINEER WEAPON BONUS", CC.Gold + "Deal +15% damage to all enemies.", CC.Gold + "120% B-2 Bomber Ability", CC.Gold + "Immune to World Destroyer Pet", CC.Gold + "Immune to Shockwave, Obliterate, Guardians", CC.Gray + "(" + CC.GrayI + "Requires all 4 engineer items." + CC.Gray + ")")).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build())).setString("cosmicType", "ENGINEER_SWORD").build();
    }
}