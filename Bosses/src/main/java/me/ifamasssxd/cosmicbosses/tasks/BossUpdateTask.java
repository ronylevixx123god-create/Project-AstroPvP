/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 */
package me.ifamasssxd.cosmicbosses.tasks;

import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossDespawnEvent;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

public class BossUpdateTask
implements Runnable {
    @Override
    public void run() {
        for (Boss boss : Boss.bosses.values()) {
            if (boss.isBossSpawned()) {
                boss.onUpdate();
                continue;
            }
            if (boss.getEntity() != null && boss.getEntity().isValid()) continue;
            if (boss.getBossType() == BossType.DUNGEON_YIJKI || boss.getBossType() == BossType.DEMONIC_WITHER || boss.getBossType() == BossType.EVIL_ABOMINATION) {
                boss.cleanup();
            }
            if (boss.getEntity() != null && !boss.getEntity().hasMetadata("killed")) {
                Bukkit.getPluginManager().callEvent((Event)new BossDespawnEvent((Entity)boss.getEntity(), boss));
                boss.getEntity().remove();
            }
            Boss.bosses.remove(boss.getUuid());
            System.out.println("Removing invalid boss!");
        }
    }
}

