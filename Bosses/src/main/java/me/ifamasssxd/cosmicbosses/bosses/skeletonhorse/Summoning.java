/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.bosses.skeletonhorse;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.UndeadAssassin;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.YijkiDisciple;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Summoning
extends BossAbility {
    Random random = new Random();

    public Summoning(Boss boss) {
        super(boss);
    }

    @Override
    public void onAbility() {
        if (this.getBoss() instanceof UndeadAssassin) {
            UndeadAssassin boss = (UndeadAssassin)this.getBoss();
            if (boss.invincible) {
                return;
            }
            boss.invincible = true;
            ((CraftEntity)this.getBoss().getEntity()).getHandle().motY += 0.5;
        } else if (this.getBoss() instanceof YijkiDisciple) {
            YijkiDisciple disk = (YijkiDisciple)this.getBoss();
            if (disk.invincible) {
                return;
            }
            disk.invincible = true;
            ((CraftEntity)this.getBoss().getEntity()).getHandle().motY += 0.5;
        }
        this.boss.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        int amount = this.random.nextInt(3) + 2;
        for (int i = 0; i < amount && this.boss.getMinions().size() <= 7; ++i) {
            Skeleton skeleton = (Skeleton)this.boss.spawnMinion(EntityType.SKELETON, this.boss.getEntity().getLocation());
            skeleton.setMaxHealth(50.0);
            skeleton.setHealth(skeleton.getMaxHealth());
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
            skeleton.getEquipment().setHelmet(new CustomItem(new ItemStack(Material.DIAMOND_HELMET)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).build());
            skeleton.getEquipment().setChestplate(new CustomItem(new ItemStack(Material.DIAMOND_CHESTPLATE)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).build());
            skeleton.getEquipment().setLeggings(new CustomItem(new ItemStack(Material.DIAMOND_LEGGINGS)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).build());
            skeleton.getEquipment().setBoots(new CustomItem(new ItemStack(Material.DIAMOND_BOOTS)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).setUnbreakable(true).build());
            skeleton.getEquipment().setItemInHand(new CustomItem(new ItemStack(Material.DIAMOND_SWORD)).setUnbreakable(true).addEnchant(Enchantment.DAMAGE_ALL, 2).build());
            skeleton.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            skeleton.setMetadata("no_drops", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        }
        this.activateCooldown();
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(40L);
    }

    @Override
    public String getAbilityName() {
        return "Summoning";
    }
}

