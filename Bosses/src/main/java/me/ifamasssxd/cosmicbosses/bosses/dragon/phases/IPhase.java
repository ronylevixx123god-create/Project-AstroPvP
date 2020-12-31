/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_8_R3.DamageSource
 *  net.minecraft.server.v1_8_R3.EntityComplexPart
 *  net.minecraft.server.v1_8_R3.EntityEnderCrystal
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.Vec3D
 *  org.bukkit.Location
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Location;

public interface IPhase {
    public boolean getIsStationary();

    public void doLocalUpdate();

    public void onCrystalDestroyed(EntityEnderCrystal var1, Location var2, DamageSource var3, EntityPlayer var4);

    public void initPhase();

    public void removeAreaEffect();

    public float getMaxRiseOrFall();

    public float getYawFactor();

    public PhaseList<? extends IPhase> getType();

    @Nullable
    public Vec3D getTargetLocation();

    public float getAdjustedDamage(EntityComplexPart var1, DamageSource var2, float var3);
}

