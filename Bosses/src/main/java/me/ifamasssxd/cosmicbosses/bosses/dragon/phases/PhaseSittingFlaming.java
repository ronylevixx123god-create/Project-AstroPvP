/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  net.minecraft.server.v1_7_R4.AxisAlignedBB
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityTrackerEntry
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.EntityEffect
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseSittingBase;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PhaseSittingFlaming
extends PhaseSittingBase {
    private int flameTicks;
    private int flameCount;
    private AxisAlignedBB flameBreathBox;

    public PhaseSittingFlaming(EndDragonEntity dragonIn) {
        super(dragonIn);
    }

    @Override
    public void doClientRenderEffects() {
        ++this.flameTicks;
        if (this.flameTicks % 2 == 0) {
            Vec3D vec3d = this.dragon.getHeadLookVec(1.0f).a();
            vec3d.b(-0.7853982f);
            double d0 = this.dragon.head.locX;
            double d1 = this.dragon.head.locY - (double)(this.dragon.head.height / 2.0f) - 2.5;
            double d2 = this.dragon.head.locZ;
            EntityTrackerEntry entry = (EntityTrackerEntry)((WorldServer)this.dragon.world).getTracker().trackedEntities.get(this.dragon.getId());
            for (int i = 0; i < 8; ++i) {
                double d3 = d0 + this.dragon.random.nextGaussian() / 2.0;
                double d4 = d1 - this.dragon.random.nextGaussian() / 2.0;
                double d5 = d2 + this.dragon.random.nextGaussian() / 2.0;
                for (int j = 0; j < 6; ++j) {
                    float offX = (float)(-vec3d.a * (double)0.08f * (double)j);
                    float offY = (float)(-vec3d.b * (double)0.6f);
                    float offZ = (float)(-vec3d.c * (double)0.08f * (double)j);
                    if (entry == null) continue;
                    entry.trackedPlayers.forEach(pl -> {
                        if (pl != null && pl.playerConnection != null && !pl.playerConnection.isDisconnected()) {
                            pl.playerConnection.sendPacket((Packet)new PacketPlayOutWorldParticles("smoke", (float)d3, (float)d4, (float)d5, offX, offY, offZ, 1.3f, 0));
                            pl.playerConnection.sendPacket((Packet)new PacketPlayOutWorldParticles("flame", (float)d3, (float)d4, (float)d5, offX, offY, offZ, 1.3f, 0));
                        }
                    });
                }
                vec3d.b(0.19634955f);
            }
            if (this.flameTicks % 30 == 0) {
                this.dragon.shootTNT();
            }
            if (this.flameBreathBox != null) {
                this.dragon.world.getEntities((Entity)this.dragon, this.flameBreathBox, sel -> sel instanceof EntityHuman).forEach(ent -> {
                    if (ent instanceof EntityHuman) {
                        Player player = (Player)((EntityHuman)ent).getBukkitEntity();
                        boolean success = false;
                        if (!player.isDead() && BossUtils.dealPercentMaxHealthDamage(player, 2.0, 1, true, true)) {
                            player.setFireTicks(60);
                            success = true;
                            player.playEffect(EntityEffect.HURT);
                        }
                        Bukkit.getLogger().info("Flamming  " + player.getName() + " because they were inside flame breath: " + success);
                    }
                });
            }
        }
    }

    @Override
    public void doLocalUpdate() {
        this.doClientRenderEffects();
        if (this.flameTicks >= 40) {
            if (this.flameCount >= 5) {
                this.dragon.motX = 0.0;
                this.dragon.motY = 0.0;
                this.dragon.motZ = 0.0;
                this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
            } else {
                this.dragon.shootTNT();
                this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
            }
        }
    }

    @Override
    public void initPhase() {
        this.flameTicks = 0;
        Vec3D lookLok = this.dragon.getHeadLookVec(1.0f);
        lookLok.a();
        this.flameBreathBox = this.dragon.head.boundingBox.grow(lookLok.a * 40.0, -lookLok.b * 30.0, lookLok.c * 40.0);
        ++this.flameCount;
        this.dragon.bossInstance.sendMessage(ChatColor.WHITE + "Behold, now you will know what it means to " + ChatColor.RED + ChatColor.BOLD.toString() + "BURN" + ChatColor.WHITE + "!", Sound.ENDERDRAGON_GROWL, 1.1f);
    }

    public PhaseList<PhaseSittingFlaming> getType() {
        return PhaseList.SITTING_FLAMING;
    }

    public void resetFlameCount() {
        this.flameCount = 0;
    }
}

