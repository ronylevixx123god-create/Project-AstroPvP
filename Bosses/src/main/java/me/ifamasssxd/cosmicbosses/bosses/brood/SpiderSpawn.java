/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.CaveSpider
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Spider
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.bosses.brood;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpiderSpawn
extends BossAbility {
    Random random = new Random();

    public SpiderSpawn(Boss boss) {
        super(boss);
    }

    @Override
    public void onAbility() {
        if (((Boss)this.getBoss()).getMinions().size() < 15) {
            int toSpawn = this.random.nextInt(5) + 7;
            boolean spawned = false;
            int forTarget = toSpawn / 5;
            Spider boss = (Spider)((Boss)this.getBoss()).getEntity();
            for (int i = 0; i < toSpawn && ((Boss)this.getBoss()).getMinions().size() < 15; ++i) {
                Player pl;
                spawned = true;
                Location spawnLoc = null;
                if (boss.getTarget() != null && forTarget > 0 && BossUtils.canAffectPlayer(pl = (Player)boss.getTarget(), false)) {
                    spawnLoc = pl.getLocation();
                    --forTarget;
                }
                if (spawnLoc == null) {
                    spawnLoc = ((Boss)this.getBoss()).getEntity().getLocation();
                }
                CaveSpider spider = (CaveSpider)this.spawnMinion(BossUtils.getRandomLocationNearby(spawnLoc, 5, 2, 2));
                spider.setMaxHealth(140.0);
                spider.setHealth(140.0);
            }
            if (spawned) {
                ((Boss)this.getBoss()).sendMessage((Object)ChatColor.ITALIC + "Go my children, devour their very souls!", Sound.ZOMBIE_REMEDY, 1.4f);
            }
        }
        this.activateCooldown();
    }

    public Entity spawnMinion(Location l) {
        Entity ent = BossUtils.createCustomEntity(CustomEntity.CAVE_SPIDER, l);
        ent.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        ent.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)((Boss)this.getBoss()).getEntity().getUniqueId()));
        ((Boss)this.getBoss()).getMinions().add(ent);
        if (ent instanceof LivingEntity) {
            LivingEntity li = (LivingEntity)ent;
            li.setCustomName(((Boss)this.getBoss()).getColor(true) + "Brood Spawn");
            li.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            if (li.getEquipment() != null) {
                li.getEquipment().setItemInHandDropChance(0.0f);
                li.getEquipment().setHelmetDropChance(0.0f);
                li.getEquipment().setChestplateDropChance(0.0f);
                li.getEquipment().setLeggingsDropChance(0.0f);
                li.getEquipment().setBootsDropChance(0.0f);
            }
        }
        return ent;
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(10L);
    }

    @Override
    public String getAbilityName() {
        return "Spider Spawn";
    }
}

