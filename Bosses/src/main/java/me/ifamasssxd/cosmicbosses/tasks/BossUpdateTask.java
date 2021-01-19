package me.ifamasssxd.cosmicbosses.tasks;

import me.ifamasssxd.cosmicbosses.struct.enums.*;
import org.bukkit.*;
import me.ifamasssxd.cosmicbosses.struct.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import java.util.*;

public class BossUpdateTask implements Runnable
{
    @Override
    public void run() {
        for (final Boss boss : Boss.bosses.values()) {
            if (boss.isBossSpawned()) {
                boss.onUpdate();
            }
            else {
                if (boss.getEntity() != null && boss.getEntity().isValid()) {
                    continue;
                }
                if (boss.getBossType() == BossType.HOLIDAY_SPIRIT || boss.getBossType() == BossType.DUNGEON_YIJKI || boss.getBossType() == BossType.DEMONIC_WITHER || boss.getBossType() == BossType.EVIL_ABOMINATION || boss.getBossType() == BossType.CHRISTMAS_SPIRIT) {
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
}
