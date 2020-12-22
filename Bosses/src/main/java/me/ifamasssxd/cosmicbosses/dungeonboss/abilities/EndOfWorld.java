/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.citizensnpcs.npc.entity.EntityHumanNPC$PlayerNPC
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EndOfWorld
extends DungeonAbility {
    private final int COUNTDOWN_TIME = (int)TimeUnit.MINUTES.toSeconds(3L);
    private int secondsCounted = 0;
    int ticks = 0;
    private boolean executed = false;

    public EndOfWorld(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {
        ++this.ticks;
        if (this.ticks % 20 == 0 && !this.executed) {
            ++this.secondsCounted;
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LAVA, (Location)this.getBoss().getEntity().getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)20);
            this.boss.getEntity().getWorld().getPlayers().forEach(pl -> pl.playSound(pl.getLocation(), Sound.NOTE_PLING, 1.0f, 1.1f));
            if (this.secondsCounted >= this.COUNTDOWN_TIME) {
                this.executed = true;
                Bukkit.getLogger().info("Killing everyone due to End Of World time running out.");
                for (Player player : ((DungeonYijki)this.boss).getWorldPlayers()) {
                    if (player instanceof EntityHumanNPC.PlayerNPC || BossUtils.isInGodMode(player) || !(player.getHealth() > 0.0)) continue;
                    player.setHealth(0.0);
                    Bukkit.getLogger().info("Killing " + player.getName());
                }
            }
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(300L);
    }

    @Override
    public boolean shouldStopTicking() {
        return this.executed;
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();
        DungeonYijki boss = (DungeonYijki)this.boss;
        for (Player pl : boss.getWorldPlayers()) {
            pl.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "End of The World >> " + (Object)ChatColor.RED + "This reality does not exist with out me. I will not die in a place of my own creation. I am deleting this dimension. All your lives will end shortly, along with this world.");
        }
    }

    @Override
    public void onAbilityDisable() {
    }

    @Override
    public void onAbilityEvent(Event event) {
    }

    @Override
    public String getAbilityName() {
        return "End of World";
    }

    public int getCOUNTDOWN_TIME() {
        return this.COUNTDOWN_TIME;
    }

    public int getSecondsCounted() {
        return this.secondsCounted;
    }
}

