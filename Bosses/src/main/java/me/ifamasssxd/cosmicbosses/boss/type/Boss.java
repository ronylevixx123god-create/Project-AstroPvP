/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import me.ifamasssxd.cosmicbosses.boss.rarity.*;

public abstract class Boss {
    public static HashMap<Entity, Boss> boss_map = new HashMap();
    protected EntityType type;
    public BossRarity rarity;
    Entity entity;
    public String custom_name;
    String sys_name;
    public double max_hp;
    public HashMap<String, Double> players_who_hit_me = new HashMap();

    public abstract void spawnBoss(Location var1);

    public abstract void onBossDamaged(EntityDamageEvent var1);

    public abstract void onBossDeath(EntityDeathEvent var1);

    public abstract void onBossDamage(EntityDamageEvent var1);
}

